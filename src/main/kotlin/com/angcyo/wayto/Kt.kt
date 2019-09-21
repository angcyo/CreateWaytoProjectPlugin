package com.angcyo.wayto

import com.angcyo.wayto.action.CreateProjectAction
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.ui.Messages

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/21
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

val L = Logger.getInstance(CreateProjectAction::class.java)

public fun String.getValue(defaultValue: String = ""): String {
    return PropertiesComponent.getInstance().getValue(this, defaultValue)
}

public fun String.putValue(value: String) {
    PropertiesComponent.getInstance().setValue(this, value)
}

public fun String.message(title: String = "注意") {
    Messages.showMessageDialog(this, title, Messages.getInformationIcon())
}