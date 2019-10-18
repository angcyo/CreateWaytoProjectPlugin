package com.angcyo.wayto.action

import com.angcyo.wayto.dialog.CreateProjectDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * Email:angcyo@126.com
 *
 * @author angcyo
 * @date 2019/10/18
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
class MenuCreateAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        CreateProjectDialog().show()
    }
}
