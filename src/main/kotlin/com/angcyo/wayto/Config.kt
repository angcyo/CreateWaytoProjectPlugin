package com.angcyo.wayto

import java.io.File

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/21
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class Config {
    var projectName: String = ""
    var moduleName: String = ""
    var packageName: String = ""
    var schema: String = ""

    var savePath: String = ""
    var corePath: String = ""
    var pluginPath: String = ""
}

public fun defaultConfig(): Config {
    return Config().apply {
        projectName = "projectName".getValue("Wayto.Reservoir.Mobile")
        moduleName = "moduleName".getValue("Reservoir")
        packageName = "packageName".getValue("com.wayto.reservoir.patrol.plugin")
        schema = "schema".getValue("Reservoir")
        savePath = "savePath".getValue("E:\\Project")
        corePath = "corePath".getValue("E:\\Project\\$projectName\\wayto.core")
        pluginPath = "pluginPath".getValue("E:\\Project\\$projectName\\wayto.plugin")
    }
}

public fun Config.save() {
    "projectName".putValue(projectName)
    "moduleName".putValue(moduleName)
    "packageName".putValue(packageName)
    "schema".putValue(schema)
    "savePath".putValue(savePath)
    "corePath".putValue(corePath)
    "pluginPath".putValue(pluginPath)
}

public fun Config.projectPath(): String = "${savePath}${File.separator}${projectName}"
public fun Config.modulePath(): String = "${projectPath()}${File.separator}${moduleName}"
public fun Config.corePath(): String = "${projectPath()}${File.separator}wayto.core"
public fun Config.pluginPath(): String = "${projectPath()}${File.separator}wayto.plugin"

public fun Config.packagePath(): String = modulePath() + "/src/main/java/" + packageName.toPath()
public fun Config.resPath(): String = modulePath() + "/src/main/res/"

