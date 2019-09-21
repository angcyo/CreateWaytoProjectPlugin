package com.angcyo.wayto

import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.wm.IdeFocusManager
import com.intellij.platform.PlatformProjectOpenProcessor
import java.io.File
import java.util.*

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/21
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
object CreateProjectHelper {
    fun createProject(config: Config): Boolean {
        val projectFile = File(config.savePath, config.projectName)

        if (projectFile.exists()) {
            if (projectFile.isFile || projectFile.list()?.isNotEmpty() == true) {
                "目录非空:${projectFile.absolutePath}".message()
            } else {
                openProject(projectFile.absolutePath)
                return true
            }
        } else if (projectFile.mkdirs()) {
            openProject(projectFile.absolutePath)
            return true
        } else {
            "无法创建目录:${projectFile.absolutePath}".message()
        }
        return false
    }

    fun openProject(path: String) {
        val frame = IdeFocusManager.getGlobalInstance().lastFocusedFrame
        val projectToClose = frame?.project

        val baseDir = LocalFileSystem.getInstance().refreshAndFindFileByIoFile(File(path))
        VfsUtil.markDirtyAndRefresh(false, true, true, baseDir)
        val options = EnumSet.noneOf(PlatformProjectOpenProcessor.Option::class.java)
        val project = PlatformProjectOpenProcessor.doOpenProject(baseDir!!, projectToClose, -1, null, options)

        L.info("打开project->${project.toString()}")
    }
}