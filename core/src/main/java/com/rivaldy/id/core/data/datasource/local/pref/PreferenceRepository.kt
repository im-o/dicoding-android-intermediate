package com.rivaldy.id.core.data.datasource.local.pref

/** Created by github.com/im-o on 10/1/2022. */

interface PreferenceRepository {
    fun setUserTokenPref(id: String)
    fun getUserTokenPref(): String
    fun clearLoginInfo() {
        setUserTokenPref("")
    }
}