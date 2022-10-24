package com.rivaldy.id.dicoding.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivaldy.id.core.data.datasource.remote.rest.RestApiRepositoryImpl
import com.rivaldy.id.core.data.model.remote.login.LoginRequest
import com.rivaldy.id.core.data.model.remote.login.LoginResponse
import com.rivaldy.id.core.data.model.remote.register.RegisterRequest
import com.rivaldy.id.core.data.model.remote.register.RegisterResponse
import com.rivaldy.id.core.data.network.DataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Created by github.com/im-o on 10/4/2022. */

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val api: RestApiRepositoryImpl
) : ViewModel() {

    private val _loginUser: MutableLiveData<DataResource<LoginResponse>> = MutableLiveData()
    private val _registerUser: MutableLiveData<DataResource<RegisterResponse>> = MutableLiveData()
    val loginUser: LiveData<DataResource<LoginResponse>> = _loginUser
    val registerUser: LiveData<DataResource<RegisterResponse>> = _registerUser

    fun loginUserApiCall(request: LoginRequest) = viewModelScope.launch {
        _loginUser.value = DataResource.Loading
        _loginUser.value = api.loginUserApiCall(request)
    }

    fun registerUserApiCall(request: RegisterRequest) = viewModelScope.launch {
        _registerUser.value = DataResource.Loading
        _registerUser.value = api.registerUserApiCall(request)
    }
}