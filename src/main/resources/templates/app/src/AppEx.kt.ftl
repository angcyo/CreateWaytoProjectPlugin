package ${packageName}

import ${packageName}.http.ApiService
import com.wayto.objectbox.RBox
import com.wayto.ui.core.http.request
import com.wayto.ui.kotlin.nowTime
import com.wayto.ui.kotlin.toTime
import io.objectbox.Box

/**
 *
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2019/09/23
 * Copyright (c) 2019 ShenZhen O&M Cloud Co., Ltd. All rights reserved.
 */
object AppEx {
    val PAGE_SIZE = 20
}

public fun isRelease() = BuildConfig.BUILD_TYPE.equals("release", true)
public fun isDebug() = BuildConfig.BUILD_TYPE.equals("debug", true)

public fun api() = request(ApiService::class.java)

public fun <T> boxOf(entityClass: Class<T>, init: Box<T>.() -> Unit = {}): Box<T> {
    val box = RBox.get(BuildConfig.PLUGIN_NAME).boxFor(entityClass)
    box.init()
    return box
}

public fun <T> T.nowTimeString(pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
    return nowTime().toTime(pattern)
}