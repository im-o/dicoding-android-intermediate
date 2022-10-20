package com.rivaldy.id.dicoding.ui

import androidx.lifecycle.ViewModel
import com.rivaldy.id.core.data.data_source.local.pref.PreferenceRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/** Created by github.com/im-o on 10/2/2022. */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val pref: PreferenceRepositoryImpl
) : ViewModel() {

    fun setUserTokenPref(token: String) = pref.setUserTokenPref(token)

    fun getUserTokenPref() = pref.getUserTokenPref()

    fun clearLoginInfo() = pref.clearLoginInfo()
}