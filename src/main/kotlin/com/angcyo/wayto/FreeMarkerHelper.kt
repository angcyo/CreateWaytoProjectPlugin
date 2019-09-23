package com.angcyo.wayto

import com.intellij.openapi.application.PathManager
import freemarker.template.Configuration
import freemarker.template.TemplateExceptionHandler
import java.io.File
import java.util.*
import java.util.regex.Pattern


/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/23
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
object FreeMarkerHelper {
    private lateinit var freemarkerConfig: Configuration
    private var templatePath: String = ""
    private val ROOT_PATH = "templates"

    init {
        try {
            freemarkerConfig = Configuration()
            freemarkerConfig.incompatibleImprovements = Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS

            val jarUrl = this.javaClass.classLoader.getResource("META-INF/plugin.xml")
            //jar
            templatePath = if (jarUrl?.protocol == "file") {
                //如果是文件, 可以直接拿到路径
                this.javaClass.classLoader.getResource("")!!.file
            } else {
                //如果是jar, 需要先解压
                val tempPath = PathManager.getTempPath() + File.separator + "CreateWaytoProject"
                val tempFile = File(tempPath, "CreateWaytoProject.jar")
                Pattern.compile("(?<=file:/).*(?=!/META-INF/plugin.xml)").matcher(jarUrl!!.file).apply {
                    find()
                    val jarFile = File(group())
                    jarFile.copyTo(tempFile, true)

                    com.intellij.util.io.ZipUtil.extract(jarFile, File(tempPath), null)
                }
                tempPath
            }
            freemarkerConfig.setDirectoryForTemplateLoading(File(templatePath, ROOT_PATH))
            freemarkerConfig.numberFormat = "#"
            freemarkerConfig.isClassicCompatible = true
            freemarkerConfig.defaultEncoding = "UTF-8"
            freemarkerConfig.locale = Locale.CHINA
            freemarkerConfig.templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER
        } catch (e: Exception) {
            L.error(e.message, e)
        }
    }

    fun parse(templateName: String, outPath: String, params: Map<String, Any>? = null) {
        //获取模版
        val template = freemarkerConfig.getTemplate(templateName)
        //创建相应目录结构
        File(outPath).apply {
            if (!exists()) {
                mkdirs()
            }
        }
        //输入文件流
        val outputStreamWriter =
            File(
                outPath + File.separator + templateName.subSequence(
                    templateName.lastIndexOf("/") + 1,
                    templateName.length - 4
                )
            ).run {
                createNewFile()
                writer()
            }
        //写入流
        template.process(params, outputStreamWriter)
        //L.info(out.toString())
    }

    fun doConfig(config: Config) {
        val dataModel = mutableMapOf<String, String>()
        dataModel["moduleName"] = config.moduleName
        dataModel["schema"] = config.schema
        dataModel["packageName"] = config.packageName

        /*--------------------project目录配置--------------------------------*/
        parse(".gitignore.ftl", config.projectPath(), dataModel)
        parse("build.gradle.ftl", config.projectPath())
        parse("gradle.properties.ftl", config.projectPath())

        //ignore 项目忽略
        parse("ignore.ftl", config.projectPath())

        parse("key_file.properties.ftl", config.projectPath())
        parse("settings.gradle.ftl", config.projectPath())

        File("$templatePath/${ROOT_PATH}/yunwei.keystore.jks.ftl").copyTo(
            File("${config.projectPath()}/yunwei.keystore.jks"),
            true
        )

        /*--------------------app目录配置--------------------------------*/

        //androidTest
        File(config.modulePath() + "/src/androidTest/java/" + config.packageName.toPath()).mkdirs()

        //Test
        File(config.modulePath() + "/src/test/java/" + config.packageName.toPath()).mkdirs()

        //src
        File(config.packagePath()).mkdirs()
        File(config.packagePath() + "/fragment").mkdirs()
        File(config.packagePath() + "/http").mkdirs()
        File(config.resPath()).mkdirs()

        parse("app/.gitignore.ftl", config.modulePath())
        parse("app/build.gradle.ftl", config.modulePath(), dataModel)
        parse("app/src/AndroidManifest.xml.ftl", config.modulePath() + "/src/main/", dataModel)

        //释放res资源
        com.intellij.util.io.ZipUtil.extract(
            File(templatePath, "res.tar"),
            File(config.modulePath() + "/src/main/"),
            null
        )

        parse("app/src/LoginFragment.kt.ftl", config.packagePath() + "/fragment/", dataModel)
        parse("app/src/MainFragment.kt.ftl", config.packagePath() + "/fragment/", dataModel)
        parse("app/src/ApiService.kt.ftl", config.packagePath() + "/http/", dataModel)
        parse("app/src/App.kt.ftl", config.packagePath(), dataModel)
        parse("app/src/AppEx.kt.ftl", config.packagePath(), dataModel)
        parse("app/src/MainActivity.kt.ftl", config.packagePath(), dataModel)
        parse("app/src/SplashActivity.kt.ftl", config.packagePath(), dataModel)
        parse("app/src/PlaceholderEntity.kt.ftl", config.packagePath(), dataModel)
    }
}

