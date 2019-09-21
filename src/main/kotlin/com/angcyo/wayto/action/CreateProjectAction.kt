package com.angcyo.wayto.action

import com.angcyo.wayto.dialog.CreateProjectDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/21
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
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
