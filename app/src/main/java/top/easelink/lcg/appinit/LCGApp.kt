package top.easelink.lcg.appinit

import android.app.Application
import android.content.Context
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.stat.StatService
import timber.log.Timber
import timber.log.Timber.DebugTree
import top.easelink.framework.guard.AppGuardStarter
import top.easelink.framework.log.ErrorReportTree
import top.easelink.lcg.BuildConfig
import top.easelink.lcg.R
import top.easelink.lcg.event.EVENT_APP_LAUNCH
import top.easelink.lcg.event.sendEvent


class LCGApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ErrorReportTree())
        }
        initBulgy()
        initMTA()
        AppGuardStarter.init(this)
    }

    private fun initBulgy() {
        Bugly.init(
            applicationContext,
            BuildConfig.BUGLY_APP_ID, false
        )
        Beta.largeIconId = R.drawable.ic_avatar_m_1
        Beta.smallIconId = R.drawable.ic_avatar_m_1
        Beta.enableHotfix = false
    }

    private fun initMTA() {
        StatService.setContext(context)
        StatService.registerActivityLifecycleCallbacks(instance)
        // fixme incompatible with Bulgy, need more investigation
//        StatCrashReporter.getStatCrashReporter(this).apply {
//            isEnableInstantReporting = true
//            javaCrashHandlerStatus = true
//        }
        sendEvent(EVENT_APP_LAUNCH)
    }

    companion object {
        lateinit var instance: LCGApp
            private set

        @JvmStatic
        val context: Context
            get() = instance

    }
}