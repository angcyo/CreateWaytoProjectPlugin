package ${packageName}.fragment

import ${packageName}.BuildConfig
import ${packageName}.R
import com.wayto.ui.base.main.JavaLoginFragment
import com.wayto.ui.core.only

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/23
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

<#noparse>
class LoginFragment : JavaLoginFragment() {

    override fun getLogoLayout(): Int {
        return -1
    }

    override fun getVersionName(): String? {
        return "当前版本 v${BuildConfig.VERSION_NAME}"
    }

    override fun onLogin(name: String, password: String) {
        super.onLogin(name, password)
        only(MainFragment())
    }
}
</#noparse>