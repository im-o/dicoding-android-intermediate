package com.rivaldy.id.core.data.data_source.remote.rest_api

import com.rivaldy.id.core.data.model.remote.DefaultResponse
import com.rivaldy.id.core.data.model.remote.login.LoginResponse
import com.rivaldy.id.core.data.model.remote.register.RegisterResponse
import com.rivaldy.id.core.data.model.remote.story.UserStoryResponse
import com.rivaldy.id.core.data.network.DataResource
import okhttp3.MultipartBody
import okhttp3.RequestBody

/** Created by github.com/im-o on 10/1/2022. */

interface RestApiRepository {
    suspend fun registerUserApiCall(name: String, email: String, password: String): DataResource<RegisterResponse>

    suspend fun loginUserApiCall(email: String, password: String): DataResource<LoginResponse>

    suspend fun getStoriesApiCall(page: Int?, size: Int?, locationType: Int?): DataResource<UserStoryResponse>

    suspend fun addStoryApiCall(description: RequestBody, photo: MultipartBody.Part, lat: RequestBody?, lon: RequestBody?): DataResource<DefaultResponse>
}