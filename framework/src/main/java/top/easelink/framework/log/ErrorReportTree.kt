package top.easelink.framework.log

import timber.log.Timber

class ErrorReportTree: Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority >= 6) {
        }
    }
}