package ${packageName}.fragment

import android.os.Bundle
import ${packageName}.BuildConfig
import ${packageName}.R
import com.wayto.ui.base.main.SaasLoginFragment
import com.wayto.ui.core.app
import com.wayto.ui.core.only
import com.wayto.ui.kotlin.dpi
import com.wayto.ui.recycler.RBaseViewHolder
import com.wayto.ui.widget.Button

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/23
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

<#noparse>
class LoginFragment : SaasLoginFragment() {
    companion object {
        val IS_LOGIN_SUCCESS = "${app().scheme}_IS_LOGIN_SUCCESS"
    }

    override fun getLogoLayout(): Int {
        return -1
    }

    override fun getVersionName(): String? {
        return BuildConfig.VERSION_NAME
    }

    override fun login(name: String, password: String, company: String) {
        only(MainFragment())
    }

    override fun initBaseView(
        viewHolder: RBaseViewHolder,
        arguments: Bundle?,
        savedInstanceState: Bundle?
    ) {
        super.initBaseView(viewHolder, arguments, savedInstanceState)

        viewHolder.button(R.id.login_button).apply {
            setButtonStyle(Button.ROUND_GRADIENT_RECT, 45 * dpi)
        }
    }
}
</#noparse>