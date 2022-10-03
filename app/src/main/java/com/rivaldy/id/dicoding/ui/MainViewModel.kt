package com.rivaldy.id.dicoding.ui

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivaldy.id.core.data.data_source.local.pref.PreferenceRepositoryImpl
import com.rivaldy.id.core.data.data_source.remote.rest_api.RestApiRepositoryImpl
import com.rivaldy.id.core.data.model.local.pref.LoginInfo
import com.rivaldy.id.core.data.model.remote.login.LoginResponse
import com.rivaldy.id.core.data.network.DataResource
import com.rivaldy.id.core.utils.UtilFunctions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Created by github.com/im-o on 10/2/2022. */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val pref: PreferenceRepositoryImpl,
    private val api: RestApiRepositoryImpl
) : ViewModel() {

    private val _loginUser: MutableLiveData<DataResource<LoginResponse>> = MutableLiveData()
    val loginUser: LiveData<DataResource<LoginResponse>> = _loginUser

    fun testing() {
        pref.setLoginInfo(LoginInfo("1", "2", "3", "4"))
        Handler(Looper.getMainLooper()).postDelayed({
            UtilFunctions.logE("DATA : ${pref.getLoginInfo()}")
        }, 100)

        viewModelScope.launch { }
    }

    fun loginUserApiCall(email: String, password: String) = viewModelScope.launch {
        _loginUser.value = DataResource.Loading
        _loginUser.value = api.loginUserApiCall(email, password)
    }
}