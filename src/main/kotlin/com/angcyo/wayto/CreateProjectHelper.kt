package com.angcyo.wayto

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.progress.util.ProgressWindow
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.wm.IdeFocusManager
import com.intellij.platform.PlatformProjectOpenProcessor
import com.jetbrains.rd.util.EnumSet
import org.gradle.internal.impldep.org.eclipse.jgit.util.FileUtils
import java.io.File
import javax.swing.SwingUtilities

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/21
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
object CreateProjectHelper {
    fun createProject(config: Config, progressWindow: ProgressWindow, onEnd: () -> Unit): Boolean {
        val projectFile = File(config.savePath, config.projectName)

        if (projectFile.exists()) {
            if (config.isOverridePath()) {
                doFirst(config, progressWindow, onEnd)
                return true
            } else if (projectFile.isFile || projectFile.list()?.isNotEmpty() == true) {
                "目录非空: ${projectFile.absolutePath}".message()
            } else {
                doFirst(config, progressWindow, onEnd)
                return true
            }
        } else if (projectFile.mkdirs()) {
            doFirst(config, progressWindow, onEnd)
            return true
        } else {
            "无法创建目录: ${projectFile.absolutePath}".message()
        }
        return false
    }

    fun doFirst(config: Config, progressWindow: ProgressWindow, onEnd: () -> Unit) {
        Thread {

            try {
                if (config.isOverridePath()) {
                    //先清空
                    val projectFile = File(config.savePath, config.projectName)
                    FileUtils.delete(projectFile, 13)
                }

                mkdirs(config)

                //初始化模版
                FreeMarkerHelper.initTemplatePath(config)

                //模版文件写入
                FreeMarkerHelper.doConfig(config)

                //core plugin 库处理
                if (config.isCorePluginFromLocal()) {
                    //复制 core plugin 文件夹
                    File(config.corePath).apply {
                        if (exists() && isDirectory && canRead()) {
                            copyRecursively(File(config.corePath()), true)
                        } else {
                            GitHelper.configCore(config)
                        }
                    }
                    File(config.pluginPath).apply {
                        if (exists() && isDirectory && canRead()) {
                            copyRecursively(File(config.pluginPath()), true)
                        } else {
                            GitHelper.configPlugin(config)
                        }
                    }
                } else {
                    //在线拉取
                    GitHelper.configCore(config)
                    GitHelper.configPlugin(config)
                }

                SwingUtilities.invokeLater {
                    onEnd()

                    //打开工程
                    val projectFile = File(config.savePath, config.projectName)
                    openProject(projectFile.absolutePath)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                SwingUtilities.invokeLater {
                    progressWindow.stop()
                    e.message?.message("创建异常!")
                }
            }
        }.start()
    }

    fun mkdirs(config: Config) {
        val moduleFile =
            File("${config.savePath}${File.separator}${config.projectName}${File.separator}${config.moduleName}")
        moduleFile.mkdirs()
    }

    fun openProject(path: String) {
        val frame = IdeFocusManager.getGlobalInstance().lastFocusedFrame
        val projectToClose = frame?.project

        val baseDir = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(File(path))
        VfsUtil.markDirtyAndRefresh(false, true, true, baseDir)

        val options = EnumSet.noneOf(PlatformProjectOpenProcessor.Option::class.java)
        options.add(PlatformProjectOpenProcessor.Option.REOPEN)
        options.add(PlatformProjectOpenProcessor.Option.TEMP_PROJECT)
        options.add(PlatformProjectOpenProcessor.Option.DO_NOT_USE_DEFAULT_PROJECT)

        ApplicationManager.getApplication().invokeLater {
            try {
                val project = PlatformProjectOpenProcessor.doOpenProject(baseDir!!, projectToClose, -1, null, options)
                //L.info("打开project->${project.toString()}")
            } catch (e: Exception) {
                //L.info("打开项目失败.")
                e.printStackTrace()
                e.message?.message("打开项目失败!")
            }
        }
    }
}