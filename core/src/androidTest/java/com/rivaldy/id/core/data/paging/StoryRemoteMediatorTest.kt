package com.rivaldy.id.core.data.paging

import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rivaldy.id.core.data.datasource.local.db.AppDatabase
import com.rivaldy.id.core.data.datasource.remote.rest.ApiService
import com.rivaldy.id.core.data.model.dummy.DummyData
import com.rivaldy.id.core.data.model.local.db.StoryEntity
import com.rivaldy.id.core.data.model.remote.DefaultResponse
import com.rivaldy.id.core.data.model.remote.login.LoginResponse
import com.rivaldy.id.core.data.model.remote.register.RegisterResponse
import com.rivaldy.id.core.data.model.remote.story.UserStoryResponse
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/** Created by github.com/im-o on 10/30/2022.  */

@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class StoryRemoteMediatorTest {

    private var mockApi: ApiService = FakeApiService()

    private var mockDb: AppDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        AppDatabase::class.java
    ).allowMainThreadQueries().build()

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runBlocking {
        val remoteMediator = StoryRemoteMediator(
            mockDb,
            mockApi,
        )
        val pagingState = PagingState<Int, StoryEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @After
    fun tearDown() {
        mockDb.clearAllTables()
    }
}

class FakeApiService : ApiService {
    override suspend fun getStories(page: Int?, size: Int?, location: Int?): UserStoryResponse {
        val pagingStories = DummyData.generateDummyUserStory().subList((page?.minus(1))?.times(size ?: 0) ?: 0, ((page?.minus(1))?.times(size ?: 0) ?: 0) + (size ?: 0))
        return UserStoryResponse(false, pagingStories, "Stories fetched successfully")
    }

    override suspend fun registerUser(name: String, email: String, password: String) = RegisterResponse()

    override suspend fun loginUser(email: String, password: String) = LoginResponse()

    override suspend fun addStory(description: RequestBody, photo: MultipartBody.Part, lat: RequestBody?, lon: RequestBody?) = DefaultResponse()
}