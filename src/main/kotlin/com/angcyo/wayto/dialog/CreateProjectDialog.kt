package com.angcyo.wayto.dialog

import com.angcyo.wayto.CreateProjectHelper
import com.angcyo.wayto.save
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

    lateinit var form: CreateProjectForm

    init {
        init()
        title = "Wayto 创建工程配置页"
    }

    override fun createCenterPanel(): JComponent? {
        return CreateProjectForm().run {
            form = this
            init()
            rootFormPanel
        }
    }

    override fun getDimensionServiceKey(): String? {
        return this.javaClass.name
    }

    override fun doOKAction() {
        var result = true
        form.readConfig().run {
            result = CreateProjectHelper.createProject(this)
            if (result) {
                save()
            }
        }
        if (result) {
            super.doOKAction()
        }
    }

    override fun doValidate(): ValidationInfo? {
        return form.validation()
    }

}