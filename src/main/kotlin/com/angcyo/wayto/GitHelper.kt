package com.angcyo.wayto

import org.gradle.internal.impldep.org.eclipse.jgit.api.Git
import org.gradle.internal.impldep.org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import org.gradle.internal.impldep.org.eclipse.jgit.util.FileUtils
import java.io.File

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/23
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
object GitHelper {
    fun configCore(config: Config) {
        Git.cloneRepository().apply {
            setURI("https://gitee.com/Wayto/Wayto.Mobile.CoreLibrary.git")
            setDirectory(File(config.corePath()))
            setCredentialsProvider(
                UsernamePasswordCredentialsProvider(config.userName, config.userPassword)
            )
            call().close()
        }
    }

    fun configPlugin(config: Config) {
        Git.cloneRepository().apply {
            setURI("https://gitee.com/Wayto/Wayto.Mobile.Plugin.git")
            setDirectory(File(config.pluginPath()))
            setCredentialsProvider(
                UsernamePasswordCredentialsProvider(config.userName, config.userPassword)
            )
            call().close()
        }
    }

    /**下载现在模板*/
    fun downloadTemplates(toPath: String) {
        val targetPathFile = File(toPath)
        FileUtils.delete(targetPathFile, 13)
        Git.cloneRepository().apply {
            setURI("https://github.com/WaytoIns/WaytoProjectTemplates.git")
            setDirectory(targetPathFile)
            call().close()
        }
    }
}