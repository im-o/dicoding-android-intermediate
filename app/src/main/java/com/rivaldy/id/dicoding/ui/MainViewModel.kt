package com.rivaldy.id.dicoding.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivaldy.id.core.data.data_source.local.pref.PreferenceRepositoryImpl
import com.rivaldy.id.core.data.data_source.remote.rest_api.RestApiRepositoryImpl
import com.rivaldy.id.core.data.model.local.pref.LoginInfo
import com.rivaldy.id.core.data.model.remote.login.LoginResponse
import com.rivaldy.id.core.data.model.remote.register.RegisterResponse
import com.rivaldy.id.core.data.network.DataResource
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
    private val _registerUser: MutableLiveData<DataResource<RegisterResponse>> = MutableLiveData()
    val loginUser: LiveData<DataResource<LoginResponse>> = _loginUser
    val registerUser: LiveData<DataResource<RegisterResponse>> = _registerUser

    fun setLoginInfo(loginInfo: LoginInfo) = pref.setLoginInfo(loginInfo)

    fun getLoginInfo() = pref.getLoginInfo()

    fun clearLoginInfo() = pref.clearLoginInfo()

    fun loginUserApiCall(email: String, password: String) = viewModelScope.launch {
        _loginUser.value = DataResource.Loading
        _loginUser.value = api.loginUserApiCall(email, password)
    }

    fun registerUserApiCall(name: String, email: String, password: String) = viewModelScope.launch {
        _registerUser.value = DataResource.Loading
        _registerUser.value = api.registerUserApiCall(name, email, password)
    }
}