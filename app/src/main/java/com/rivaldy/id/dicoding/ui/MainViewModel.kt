package com.rivaldy.id.dicoding.ui

import androidx.lifecycle.ViewModel
import com.rivaldy.id.core.data.data_source.local.pref.PreferenceRepositoryImpl
import com.rivaldy.id.core.data.data_source.remote.rest_api.RestApiRepositoryImpl
import com.rivaldy.id.core.data.model.local.pref.LoginInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/** Created by github.com/im-o on 10/2/2022. */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val pref: PreferenceRepositoryImpl,
    private val api: RestApiRepositoryImpl
) : ViewModel() {

    fun setLoginInfo(loginInfo: LoginInfo) = pref.setLoginInfo(loginInfo)

    fun getLoginInfo() = pref.getLoginInfo()

    fun clearLoginInfo() = pref.clearLoginInfo()
}