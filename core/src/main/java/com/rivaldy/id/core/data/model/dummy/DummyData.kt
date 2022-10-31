package com.rivaldy.id.core.data.model.dummy

import com.rivaldy.id.core.data.model.local.db.StoryEntity
import com.rivaldy.id.core.data.model.remote.login.LoginRequest
import com.rivaldy.id.core.data.model.remote.login.LoginResponse
import com.rivaldy.id.core.data.model.remote.login.LoginResult
import com.rivaldy.id.core.data.model.remote.register.RegisterRequest
import com.rivaldy.id.core.data.model.remote.register.RegisterResponse
import com.rivaldy.id.core.data.model.remote.story.Story
import com.rivaldy.id.core.data.network.DataResource

/**
 * Created by github.com/im-o on 10/30/2022.
 */

object DummyData {
    fun generateDummyUserStory(): MutableList<Story> {
        val stories: MutableList<Story> = arrayListOf()
        for (i in 0..100) {
            val story = Story(
                "2021-10-30T07:00:00.000Z",
                "description + $i",
                i.toString(),
                "name + $i",
                "https://picsum.photos/200/300",
                0.0,
                0.0
            )
            stories.add(story)
        }
        return stories
    }

    fun generateDummyStoryEntity(): MutableList<StoryEntity> {
        val stories: MutableList<StoryEntity> = arrayListOf()
        for (i in 0..100) {
            val story = StoryEntity(
                i.toString(),
                "name + $i",
                "2021-10-30T07:00:00.000Z",
                "description + $i",
                "https://picsum.photos/200/300",
            )
            stories.add(story)
        }
        return stories
    }

    fun dummyRegisterRequest() = RegisterRequest("rivaldy", "rival@gmail.com", "rival123")
    fun dummyRegisterSuccess() = DataResource.Success(RegisterResponse(true, "User Created"))
    fun dummyRegisterFailure() = DataResource.Failure(false, 400, null, "Email is already taken")

    fun dummyLoginRequest() = LoginRequest("rival@gmail.com", "rival123")
    fun dummyLoginSuccess() = DataResource.Success(LoginResponse(false, LoginResult("rival", "some token", "rivalId")))
    fun dummyLoginFailure() = DataResource.Failure(false, 401, null, "Invalid password")
}
