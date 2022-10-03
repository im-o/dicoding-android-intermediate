package com.rivaldy.id.core.data.data_source.remote.rest_api

import com.rivaldy.id.core.data.data_source.remote.SafeApiCallRepository
import javax.inject.Inject

/** Created by github.com/im-o on 10/1/2022. */

class RestApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : RestApiRepository, SafeApiCallRepository() {

    override suspend fun registerUserApiCall(name: String, email: String, password: String) = safeApiCall {
        apiService.registerUser(name, email, password)
    }

    override suspend fun loginUserApiCall(email: String, password: String) = safeApiCall {
        apiService.loginUser(email, password)
    }
}