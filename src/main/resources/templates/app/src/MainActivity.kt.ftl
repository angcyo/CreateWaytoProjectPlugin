package ${packageName}

import android.os.Bundle
import ${packageName}.fragment.LoginFragment
import com.wayto.ui.base.helper.FragmentHelper
import com.wayto.ui.utils.RCrashHandler

open class MainActivity : SplashActivity() {

    override fun toMain() {
        FragmentHelper.restoreShow(
            this,
            supportFragmentManager,
            R.id.frame_layout,
            LoginFragment::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        OfflineManager.init(object : OfflineTaskProvider() {
//            override fun getOfflineTaskList(): List<OfflineTask> {
//                return taskList
//            }
//
//            override fun saveOfflineTask(task: OfflineTask) {
//                super.saveOfflineTask(task)
//                taskList.add(task)
//            }
//
//        }, SaasFormAttachManager())
    }

    override fun checkCrash() {
        //super.checkCrash()
        if (!isRelease()) {
            RCrashHandler.checkCrash(this)
        }
    }

    override fun needCheckBackPressed(): Boolean {
        return true
    }
}
