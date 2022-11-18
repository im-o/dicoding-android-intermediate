package com.rivaldy.id.core.data.datasource.remote.rest

import com.rivaldy.id.core.data.model.remote.DefaultResponse
import com.rivaldy.id.core.data.model.remote.login.LoginRequest
import com.rivaldy.id.core.data.model.remote.login.LoginResponse
import com.rivaldy.id.core.data.model.remote.register.RegisterRequest
import com.rivaldy.id.core.data.model.remote.register.RegisterResponse
import com.rivaldy.id.core.data.model.remote.story.UserStoryResponse
import com.rivaldy.id.core.data.network.DataResource
import okhttp3.MultipartBody
import okhttp3.RequestBody

/** Created by github.com/im-o on 10/1/2022. */

interface RestApiRepository {
    suspend fun registerUserApiCall(request: RegisterRequest): DataResource<RegisterResponse>

    suspend fun loginUserApiCall(request: LoginRequest): DataResource<LoginResponse>

    suspend fun getStoriesApiCall(page: Int?, size: Int?, locationType: Int?): DataResource<UserStoryResponse>

    suspend fun addStoryApiCall(description: RequestBody, photo: MultipartBody.Part, lat: RequestBody?, lon: RequestBody?): DataResource<DefaultResponse>
}