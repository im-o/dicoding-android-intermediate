package com.rivaldy.id.core.data.datasource.local.pref

import android.content.SharedPreferences
import com.rivaldy.id.core.utils.UtilConstantPreferences.PREF_KEY_USER_TOKEN
import javax.inject.Inject

/** Created by github.com/im-o on 10/1/2022. */

class PreferenceRepositoryImpl @Inject constructor(
    private val sharedPref: SharedPreferences
) : PreferenceRepository {

    override fun setUserTokenPref(id: String) {
        sharedPref.edit().putString(PREF_KEY_USER_TOKEN, id).apply()
    }

    override fun getUserTokenPref(): String {
        return sharedPref.getString(PREF_KEY_USER_TOKEN, null) ?: ""
    }
}