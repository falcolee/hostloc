package top.easelink.lcg.appinit

import android.app.Application
import android.content.Context
import timber.log.Timber
import timber.log.Timber.DebugTree
import top.easelink.framework.guard.AppGuardStarter
import top.easelink.framework.log.ErrorReportTree
import top.easelink.lcg.BuildConfig


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

    companion object {
        lateinit var instance: LCGApp
            private set

        @JvmStatic
        val context: Context
            get() = instance

    }
}