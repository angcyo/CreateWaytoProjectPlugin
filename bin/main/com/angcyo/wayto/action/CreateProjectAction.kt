package com.angcyo.wayto.action

import com.angcyo.wayto.dialog.CreateProjectDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class CreateProjectAction : AnAction() {
    override fun actionPerformed(anActionEvent: AnActionEvent) {
//        JBPopupFactory.getInstance().createMessage("message").showInFocusCenter()
//
//        Messages.showMessageDialog("Hello World !", "Information", Messages.getInformationIcon());
//
//        if (SampleDialogWrapper().showAndGet()) {
//            // user pressed ok
//            L.warn("this1...")
//        } else {
//            L.info("this2...")
//        }

        CreateProjectDialog().show()
    }
}
