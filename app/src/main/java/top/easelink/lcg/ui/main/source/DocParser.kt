package top.easelink.lcg.ui.main.source

import androidx.annotation.WorkerThread
import org.greenrobot.eventbus.EventBus
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import timber.log.Timber
import top.easelink.lcg.R
import top.easelink.lcg.appinit.LCGApp
import top.easelink.lcg.account.UserInfo
import top.easelink.lcg.ui.main.model.AntiScrapingException
import top.easelink.lcg.ui.main.model.NewMessageEvent
import top.easelink.lcg.ui.main.model.NotificationInfo
import top.easelink.lcg.ui.profile.model.ExtraUserInfo
import top.easelink.lcg.utils.showMessage
import kotlin.jvm.Throws

@WorkerThread
fun parseExtraUserInfoProfilePage(content: String): List<ExtraUserInfo> {
    return Jsoup.parse(content).let {
        it.getElementsByTag("dt").zip(it.getElementsByTag("dd")).map { pairs ->
            ExtraUserInfo(pairs.first.text(), pairs.second.text())
        }
    }
}

@WorkerThread
@Throws(NullPointerException::class, AntiScrapingException::class)
fun parseUserInfo(doc: Document): UserInfo {
    with(doc) {
        val userName = doc.selectFirst("a.xw1")?.text()
        when {
            userName.isNullOrEmpty() -> {
                val message = getElementById("messagetext")?.text()
                if (message.isNullOrEmpty()) {
                    throw AntiScrapingException()
                } else {
                    showMessage(message)
                    return UserInfo.getDefaultUserInfo()
                }
            }
            userName == LCGApp.context.getString(R.string.login_or_register) -> {
                return UserInfo.getDefaultUserInfo()
            }
            else -> {
                val avatar = selectFirst("a.avtm > img")?.attr("src")
                val groupInfo = selectFirst("span.xi2 > a")?.text()
                val infoList =
                    getElementById("psts").selectFirst("ul.pf_l").getElementsByTag("li").also {
                        it.forEach { il ->
                            il.selectFirst("em").appendText(" : ")
                        }
                    }
                return UserInfo(
                    userName = userName,
                    avatarUrl = avatar,
                    groupInfo = groupInfo,
                    credit = infoList[1].text(),
                    hostCoin = infoList[3].text(),
                    enthusiasticValue = infoList[2].text(),
                )
            }
        }
    }
}

@WorkerThread
fun checkMessages(doc: Document) {
    val notificationInfo = parseNotificationInfo(doc)
    if (notificationInfo.isNotEmpty()) {
        EventBus.getDefault().post(NewMessageEvent(notificationInfo))
    }
}

@WorkerThread
fun extractFormHash(doc: Document): String? {
    return doc.selectFirst("input[name=formhash]")?.attr("value")
}

@WorkerThread
fun parseNotificationInfo(doc: Document): NotificationInfo {
    with(doc) {
        val menu: Element? = getElementById("myprompt_menu")
        val requestList = mutableListOf<String>()
        var message = 0
        var follower = 0
        var myPost = 0
        var systemNotifs = 0
        menu?.select("li")?.forEach {
            try {
                it.select("a > span").first()?.text()?.also { v ->
                    Timber.d(it.toString())
                    if (v.isNotBlank() && v.toInt() >= 1) {
                        it.selectFirst("a")?.attr("href")?.also { url ->
                            requestList.add(url)
                            when {
                                url.contains("mypost") -> myPost++
                                url.contains("follower") -> follower++
                                url.contains("pm") -> message++
                                url.contains("system") -> systemNotifs++
                                else -> {
                                    // do nothing
                                }
                            }
                        }
                    }
                }
            } catch (e: NumberFormatException) {
                // don't care
            }
        }
        return NotificationInfo(message, follower, myPost, systemNotifs)
    }
}