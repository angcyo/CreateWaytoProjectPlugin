package com.angcyo.wayto

import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.wm.IdeFocusManager
import com.intellij.platform.PlatformProjectOpenProcessor
import java.io.File
import java.util.*
import javax.swing.SwingUtilities

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/21
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
object CreateProjectHelper {
    fun createProject(config: Config, onEnd: () -> Unit): Boolean {
        val projectFile = File(config.savePath, config.projectName)

        if (projectFile.exists()) {
            if (projectFile.isFile || projectFile.list()?.isNotEmpty() == true) {
                "目录非空: ${projectFile.absolutePath}".message()
            } else {
                doFirst(config, onEnd)
                return true
            }
        } else if (projectFile.mkdirs()) {
            doFirst(config, onEnd)
            return true
        } else {
            "无法创建目录: ${projectFile.absolutePath}".message()
        }
        return false
    }

    fun doFirst(config: Config, onEnd: () -> Unit) {
        Thread {
            mkdirs(config)

            //模版文件写入
            FreeMarkerHelper.doConfig(config)

            //复制 core plugin 文件夹
            File(config.corePath).apply {
                if (exists() && isDirectory && canRead()) {
                    copyRecursively(File(config.corePath()), true)
                }
            }
            File(config.pluginPath).apply {
                if (exists() && isDirectory && canRead()) {
                    copyRecursively(File(config.pluginPath()), true)
                }
            }

            SwingUtilities.invokeLater {
                onEnd()

                //打开工程
                val projectFile = File(config.savePath, config.projectName)
                openProject(projectFile.absolutePath)
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
        val project = PlatformProjectOpenProcessor.doOpenProject(baseDir!!, projectToClose, -1, null, options)

        //L.info("打开project->${project.toString()}")
    }
}