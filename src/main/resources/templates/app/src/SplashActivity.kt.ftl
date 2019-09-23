package ${packageName}

import android.Manifest
import android.os.Bundle
import com.wayto.ui.base.helper.ActivityHelper
import com.wayto.ui.base.main.WTBasePermissionActivity
import com.wayto.ui.widget.group.FragmentSwipeBackLayout

open class SplashActivity : WTBasePermissionActivity() {

    override fun needPermissions(): Array<String> {
        return arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE/*,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA*/
        )
    }

    override fun toMain() {
        ActivityHelper.build(this@SplashActivity)
            .setClass(MainActivity::class.java)
            .noAnim()
            .doIt()

        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<FragmentSwipeBackLayout>(R.id.frame_layout)?.setDimStatusBar(true)
    }
}
