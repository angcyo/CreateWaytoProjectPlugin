package com.angcyo.wayto.dialog

import com.angcyo.wayto.CreateProjectHelper
import com.angcyo.wayto.save
import com.intellij.openapi.application.ApplicationInfo
import com.intellij.openapi.application.PathManager
import com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase
import com.intellij.openapi.progress.util.ProgressWindow
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import form.CreateProjectForm
import javax.swing.JComponent

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/21
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class CreateProjectDialog : DialogWrapper(null, true) {

    private lateinit var form: CreateProjectForm

    init {
        init()
        title = "Wayto 创建工程配置页 V1.2.0"
    }

    override fun createCenterPanel(): JComponent? {
        return CreateProjectForm().run {
            form = this
            init()
            showAppInfo(buildString {
                ApplicationInfo.getInstance().let {
                    appendln(it.versionName)
                    appendln(it.fullVersion)
                    appendln(it.apiVersion)
                    appendln(it.companyName)
                    appendln(it.shortCompanyName)
                    append(PathManager.getTempPath())
                }
            })
            rootFormPanel
        }
    }

    override fun getDimensionServiceKey(): String? {
        return this.javaClass.name
    }

    override fun doOKAction() {
        val progressWindow = ProgressWindow(true, null)
        progressWindow.title = "正在创建,请稍等..."
        progressWindow.addStateDelegate(object : AbstractProgressIndicatorExBase() {
            override fun cancel() {
                super.cancel()
                progressWindow.stop()
            }
        })

        var result = true
        form.readConfig().run {
            save()

            result = CreateProjectHelper.createProject(this, progressWindow) {
                progressWindow.stop()
                if (result) {
                    super.doOKAction()
                }
            }

            if (result) {
                progressWindow.start()
            }
        }
    }

    override fun doValidate(): ValidationInfo? {
        return form.validation()
    }
}