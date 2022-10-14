package com.rivaldy.id.core.data.model.remote.story


import com.google.gson.annotations.SerializedName

data class UserStoryResponse(
    @SerializedName("error")
    val error: Boolean? = null,
    @SerializedName("listStory")
    val listStory: MutableList<Story>? = null,
    @SerializedName("message")
    val message: String? = null
)