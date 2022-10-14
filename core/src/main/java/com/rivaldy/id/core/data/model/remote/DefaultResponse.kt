package com.rivaldy.id.core.data.model.remote


import com.google.gson.annotations.SerializedName

data class DefaultResponse(
    @SerializedName("error")
    val error: Boolean? = null,
    @SerializedName("message")
    val message: String? = null
)