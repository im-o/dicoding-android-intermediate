package com.rivaldy.id.core.data.model.dummy

import com.rivaldy.id.core.data.model.local.db.StoryEntity
import com.rivaldy.id.core.data.model.remote.story.Story
import com.rivaldy.id.core.data.model.remote.story.UserStoryResponse

/**
 * Created by github.com/im-o on 10/30/2022.
 */

object DummyData {
    fun generateDummyUserStoryResponse(): UserStoryResponse {
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
        return UserStoryResponse(false, stories, "Stories fetched successfully")
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
}
