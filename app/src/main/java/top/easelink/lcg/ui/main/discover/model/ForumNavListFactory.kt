package top.easelink.lcg.ui.main.discover.model

import android.content.Context
import top.easelink.lcg.R
import top.easelink.lcg.account.UserDataRepo
import top.easelink.lcg.utils.WebsiteConstant
import java.util.*

fun generateAllForums(context: Context): List<ForumNavigationModel> {
    val list: MutableList<ForumNavigationModel> = ArrayList()
    list.add(
        ForumNavigationModel(
            context.getString(R.string.forum_us_vps),
            R.drawable.ic_message_black_24dp,
            WebsiteConstant.USVPS_QUERY
        )
    )
    list.add(
        ForumNavigationModel(
            context.getString(R.string.forum_ganhuo),
            R.drawable.ic_message_black_24dp,
            WebsiteConstant.GANHUO_QUERY
        )
    )
    list.add(
        ForumNavigationModel(
            context.getString(R.string.forum_offers),
            R.drawable.ic_message_black_24dp,
            WebsiteConstant.OFFERS_QUERY
        )
    )
    list.add(
        ForumNavigationModel(
            context.getString(R.string.forum_domains),
            R.drawable.ic_message_black_24dp,
            WebsiteConstant.DOMAINS_QUERY
        )
    )
    return list
}