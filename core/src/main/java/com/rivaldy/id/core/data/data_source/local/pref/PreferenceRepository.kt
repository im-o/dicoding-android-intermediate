package com.rivaldy.id.core.data.data_source.local.pref

import com.rivaldy.id.core.data.model.local.pref.LoginInfo

/** Created by github.com/im-o on 10/1/2022. */

interface PreferenceRepository {
    fun setUserIdPref(id: String)
    fun getUserIdPref(): String
    fun setUserEmailPref(id: String)
    fun getUserEmailPref(): String
    fun setUserTokenPref(id: String)
    fun getUserTokenPref(): String
    fun setUserNamePref(id: String)
    fun getUserNamePref(): String

    fun setLoginInfo(login: LoginInfo) {
        setUserIdPref(login.userId ?: "")
        setUserEmailPref(login.email ?: "")
        setUserTokenPref(login.token ?: "")
        setUserNamePref(login.name ?: "")
    }

    fun getLoginInfo() = LoginInfo(getUserIdPref(), getUserTokenPref(), getUserEmailPref(), getUserNamePref())

    fun clearLoginInfo() {
        setUserIdPref("")
        setUserEmailPref("")
        setUserTokenPref("")
        setUserNamePref("")
    }
}