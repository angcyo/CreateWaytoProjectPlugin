package ${packageName}

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.SparseArray
import android.view.View
import com.angcyo.http.CacheInterceptor
import com.angcyo.http.TokenInterceptor
import com.angcyo.lib.L
import com.wayto.objectbox.RBox
import com.wayto.plugin.PluginApplication
import com.wayto.ui.base.BaseTitleFragment
import com.wayto.ui.base.BaseUI
import com.wayto.ui.core.WaytoUI
import com.wayto.ui.core.app
import com.wayto.ui.kotlin.*
import com.wayto.ui.offline.OfflineCacheAdapter
import com.wayto.ui.utils.OnBackgroundObserver
import com.wayto.ui.utils.RBackground
import com.wayto.ui.utils.RNetwork
import com.wayto.ui.widget.ImageTextView
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.CountDownLatch

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/23
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */

class App : PluginApplication() {

    companion object {
        var baseAppUrl: String? = null
            get() {
                if (field != null) {
                    return field
                }
                return BuildConfig.CUSTOM_URL
            }
    }

    override fun getTokenListener(): TokenInterceptor.OnTokenListener {
        return object : TokenInterceptor.TokenListenerAdapter() {
            override fun initToken(originRequest: Request): Request {
                return originRequest.newBuilder()
                    .addHeader(
                        "Authorization",
                        "Credential Demo"
                    )
                    .addHeader("Content-Type", "application/json")
                    .build()
            }

            override fun ignoreRequest(originRequest: Request): Boolean {
                return /*UserModel.userJson == null *//*未登录的情况下, 忽略所有请求的token检查*//*
                        ||*/ !RNetwork.isConnect(app()) /*无网络*/
                        || originRequest.url().toString().contains("Authentication")
            }

            override fun isTokenInvalid(response: Response, bodyString: String): Boolean {
                val tokenInvalid =
                    bodyString.startsWith("{") && bodyString.json()?.getInt("code") == 401

//                if (tokenInvalid && UserModel.userJson != null) {
//                    Rx.onMain {
//                        Tip.tip("您已下线!")
//                    }
//                }

                return tokenInvalid
            }

            override fun tryGetToken(latch: CountDownLatch) {
//                api().fetch(
//                    API.LOGIN,
//                    jsonObject {
//                        add("Namespace", SaasLoginFragment.getLastCompanyCode())
//                        add("Identity", SaasLoginFragment.getLastUserName())
//                        add("Password", SaasLoginFragment.getLastPassword())
//                        add("isRetry", true)
//                    }
//                ).load { data, error ->
//
//                    if (error == null && data != null) {
//                        UserModel.userJson = data.body() as? JsonObject
//                    }
//
//                    latch.countDown()
//                }
                latch.countDown()
            }
        }
    }

    override fun getBaseUrl(): String {
        return baseAppUrl!!
    }

    override fun getScheme(): String {
        return BuildConfig.SCHEMA
    }

    override fun onBaseCreate() {
        super.onBaseCreate()

        L.init(BuildConfig.SHOW_DEBUG, scheme)

        RBox.init(this, BuildConfig.PLUGIN_NAME, BuildConfig.SCHEMA, BuildConfig.SHOW_DEBUG)
    }

    override fun onCreateOnce() {
        super.onCreateOnce()

        BaseUI.uiFragment = object : WaytoUI.WTDefaultUIFragment() {
            override fun initBaseTitleLayout(titleFragment: BaseTitleFragment, arguments: Bundle?) {
                super.initBaseTitleLayout(titleFragment, arguments)

                titleFragment.viewResConfig.titleTextSize =
                    getDimen(R.dimen.wt_title_text_size).toFloat()
                titleFragment.viewResConfig.titleTextColor = getColor(R.color.wt_main_text_color)
                titleFragment.viewResConfig.titleBarBackgroundColor = Color.WHITE

                //标题栏 返回按钮
                titleFragment.titleControl()
                    .selector(R.id.base_title_bar_layout)
//                    .setBackground(
//                        RDrawable.get(this@App)
//                            .gradientType(GradientDrawable.LINEAR_GRADIENT)
//                            .gradientColors(
//                                SkinHelper.getSkin().themeColor,
//                                SkinHelper.getSkin().themeDarkColor
//                            )
//                            .get()
//                    )
                    .setBackgroundColor(titleFragment.viewResConfig.titleBarBackgroundColor)
                    .selector(R.id.base_title_view)
                    .setTextBold(true)
                    .setTextSize(titleFragment.viewResConfig.titleTextSize)
                    .setTextColor(titleFragment.viewResConfig.titleTextColor)

                titleFragment.hideTitleShadow()

//                //标题栏 横线
//                titleFragment.showTitleLine(
//                    titleFragment.getColor(R.color.wt_line),
//                    titleFragment.getDimen(R.dimen.wt_line)
//                )
                //内容背景颜色
                titleFragment.viewResConfig.fragmentBackgroundColor = getColor(R.color.wt_dark_bg2)
                titleFragment.rootControl().selector()
                    .setBackgroundColor(titleFragment.viewResConfig.fragmentBackgroundColor)
            }

            override fun createBackItem(titleFragment: BaseTitleFragment): View {
                titleFragment.viewResConfig.titleItemTextColor =
                    getColor(R.color.wt_main_text_color)
                titleFragment.viewResConfig.titleItemIconColor =
                    getColor(R.color.wt_main_text_color)
                val backItem: ImageTextView = super.createBackItem(titleFragment) as ImageTextView
                backItem.apply {
                    setImageResource(R.drawable.ic_app_back)
                    imageTintList =
                        ColorStateList.valueOf(titleFragment.viewResConfig.titleItemIconColor)
                    textShowColor = titleFragment.viewResConfig.titleItemTextColor
                    //showTextSize = titleFragment.getDimen(R.dimen.wt_main_text_size).toFloat()
                    setPaddingHorizontal(6 * dpi)
                }
                return backItem
            }
        }

        RBackground.init(this, object : OnBackgroundObserver {
            override fun onActivityChanged(stack: SparseArray<String>, background: Boolean) {
                L.d("$stack")
                if (background) {
                    //切换到后台
                }
            }
        })
    }

    /**
     * 离线缓存配置
     */
    var cacheInterceptor: CacheInterceptor? = null

    override fun buildCacheInterceptor(): CacheInterceptor? {
        if (cacheInterceptor == null) {
            OfflineCacheAdapter.IGNORE_URL_PATTERNS.add(".*Authentication.*")
            OfflineCacheAdapter.IGNORE_URL_PATTERNS.add(".*oauth.*")
            cacheInterceptor = CacheInterceptor().apply {
                registerCacheAdapter(OfflineCacheAdapter())
            }
        }
        return cacheInterceptor
    }
}