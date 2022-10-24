package com.rivaldy.id.core.data.datasource.remote.rest

import com.rivaldy.id.core.data.datasource.remote.SafeApiCallRepository
import com.rivaldy.id.core.data.model.remote.login.LoginRequest
import com.rivaldy.id.core.data.model.remote.register.RegisterRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

/** Created by github.com/im-o on 10/1/2022. */

class RestApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : RestApiRepository, SafeApiCallRepository() {

    override suspend fun registerUserApiCall(request: RegisterRequest) = safeApiCall {
        apiService.registerUser(request.name ?: "", request.email ?: "", request.password ?: "")
    }

    override suspend fun loginUserApiCall(request: LoginRequest) = safeApiCall {
        apiService.loginUser(request.email ?: "", request.password ?: "")
    }

    override suspend fun getStoriesApiCall(page: Int?, size: Int?, locationType: Int?) = safeApiCall {
        apiService.getStories(page, size, locationType)
    }

    override suspend fun addStoryApiCall(description: RequestBody, photo: MultipartBody.Part, lat: RequestBody?, lon: RequestBody?) = safeApiCall {
        apiService.addStory(description, photo, lat, lon)
    }
}