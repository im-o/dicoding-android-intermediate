package com.rivaldy.id.core.data.model.remote.login


import com.google.gson.annotations.SerializedName

data class LoginResult(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("userId")
    val userId: String? = null
)