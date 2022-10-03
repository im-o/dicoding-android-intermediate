package com.rivaldy.id.core.data.model.remote.login


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("error")
    val error: Boolean? = null,
    @SerializedName("loginResult")
    val loginResult: LoginResult? = null,
    @SerializedName("message")
    val message: String? = null
)