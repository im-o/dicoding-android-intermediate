package com.rivaldy.id.core.data.data_source.remote.rest_api

import com.rivaldy.id.core.data.model.remote.login.LoginResponse
import com.rivaldy.id.core.data.model.remote.register.RegisterResponse
import com.rivaldy.id.core.data.network.DataResource

/** Created by github.com/im-o on 10/1/2022. */

interface RestApiRepository {
    suspend fun registerUserApiCall(name: String, email: String, password: String): DataResource<RegisterResponse>

    suspend fun loginUserApiCall(email: String, password: String): DataResource<LoginResponse>
}