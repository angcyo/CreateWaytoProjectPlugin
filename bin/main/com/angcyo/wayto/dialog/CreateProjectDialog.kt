package com.angcyo.wayto.dialog

import com.intellij.openapi.ui.DialogWrapper
import form.CreateProjectForm
import javax.swing.JComponent

class CreateProjectDialog : DialogWrapper(null, true) {
    init {
        init()
        title = "Wayto 创建工程配置页"
    }

    override fun createCenterPanel(): JComponent? {
        return CreateProjectForm().run {

            addBrowseButton()

            rootFormPanel
        }
    }

    override fun getDimensionServiceKey(): String? {
        return this.javaClass.name
    }
}