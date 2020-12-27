package top.easelink.lcg.account

import top.easelink.lcg.R
import top.easelink.lcg.appinit.LCGApp

class UserInfo(
    userName: String?,
    groupInfo: String? = null,
    avatarUrl: String? = null,
    hostCoin: String? = null,
    credit: String? = null,
    enthusiasticValue: String? = null,
) {

    var userName: String? = userName
        private set
    var avatarUrl: String? = avatarUrl
        private set
    var groupInfo: String? = groupInfo
        private set
    var hostCoin: String? = hostCoin
        private set
    var credit: String? = credit
        private set
    var enthusiasticValue: String? = enthusiasticValue
        private set

    override fun equals(other: Any?): Boolean {
        return (other is UserInfo
                && userName == other.userName
                && avatarUrl == other.avatarUrl
                && groupInfo == other.groupInfo
                && hostCoin == other.hostCoin
                && credit == other.credit)
    }

    override fun hashCode(): Int {
        var result = userName?.hashCode() ?: 0
        result = 31 * result + (avatarUrl?.hashCode() ?: 0)
        result = 31 * result + (groupInfo?.hashCode() ?: 0)
        result = 31 * result + (hostCoin?.hashCode() ?: 0)
        result = 31 * result + (credit?.hashCode() ?: 0)
        return result
    }

    companion object {

        fun getDefaultUserInfo(): UserInfo {
            val context = LCGApp.context
            val placeholder = context.getString(R.string.me_tab_placeholder)
            return  UserInfo(
                userName = context.getString(R.string.me_tab_username_placeholder),
                groupInfo = context.getString(R.string.me_tab_group_placeholder),
                avatarUrl = "",
                hostCoin = placeholder,
                credit = placeholder,
                enthusiasticValue = placeholder,
            )
        }
    }

}