package top.easelink.lcg.appinit

import android.app.Application
import android.content.Context
import timber.log.Timber
import timber.log.Timber.DebugTree
import top.easelink.framework.guard.AppGuardStarter
import top.easelink.framework.log.ErrorReportTree
import top.easelink.lcg.BuildConfig
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import top.easelink.lcg.R


class LCGApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ErrorReportTree())
        }
        AppGuardStarter.init(this)
    }

    private fun initBulgy() {
        Bugly.init(
            applicationContext,
            BuildConfig.BUGLY_APP_ID, false
        )
        Beta.largeIconId = R.drawable.ic_launcher_foreground
        Beta.smallIconId = R.drawable.ic_launcher_foreground
        Beta.enableHotfix = false
    }

    companion object {
        lateinit var instance: LCGApp
            private set

        @JvmStatic
        val context: Context
            get() = instance

    }
}