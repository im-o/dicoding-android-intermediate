package com.rivaldy.id.core.data.data_source.local.pref

import android.content.SharedPreferences
import com.rivaldy.id.core.utils.UtilConstantPreferences.PREF_KEY_USER_EMAIL
import com.rivaldy.id.core.utils.UtilConstantPreferences.PREF_KEY_USER_ID
import com.rivaldy.id.core.utils.UtilConstantPreferences.PREF_KEY_USER_NAME
import com.rivaldy.id.core.utils.UtilConstantPreferences.PREF_KEY_USER_TOKEN
import javax.inject.Inject

/** Created by github.com/im-o on 10/1/2022. */

class PreferenceRepositoryImpl @Inject constructor(
    private val sharedPref: SharedPreferences
) : PreferenceRepository {

    override fun setUserIdPref(id: String) {
        sharedPref.edit().putString(PREF_KEY_USER_ID, id).apply()
    }

    override fun getUserIdPref(): String {
        return sharedPref.getString(PREF_KEY_USER_ID, null) ?: ""
    }

    override fun setUserEmailPref(id: String) {
        sharedPref.edit().putString(PREF_KEY_USER_EMAIL, id).apply()
    }

    override fun getUserEmailPref(): String {
        return sharedPref.getString(PREF_KEY_USER_EMAIL, null) ?: ""
    }

    override fun setUserTokenPref(id: String) {
        sharedPref.edit().putString(PREF_KEY_USER_TOKEN, id).apply()
    }

    override fun getUserTokenPref(): String {
        return sharedPref.getString(PREF_KEY_USER_TOKEN, null) ?: ""
    }

    override fun setUserNamePref(id: String) {
        sharedPref.edit().putString(PREF_KEY_USER_NAME, id).apply()
    }

    override fun getUserNamePref(): String {
        return sharedPref.getString(PREF_KEY_USER_NAME, null) ?: ""
    }
}