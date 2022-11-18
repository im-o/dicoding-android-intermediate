package com.rivaldy.id.core.data.datasource.local.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rivaldy.id.core.data.datasource.local.db.dao.StoryDao
import com.rivaldy.id.core.data.model.dummy.DummyData
import com.rivaldy.id.core.data.model.local.db.StoryEntity
import com.rivaldy.id.core.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

/** Created by github.com/im-o on 11/1/2022.  */


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DbRepositoryImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var storyDao = mock(StoryDao::class.java)

    private lateinit var repository: DbRepositoryImpl

    @Before
    fun setUp() {
        repository = DbRepositoryImpl(storyDao)
    }

    @Test
    fun `when insert Story List, then Success Insert to Database`() = runTest {
        val storyEntity = DummyData.generateDummyStoryEntity()

        repository.insertStoriesDb(storyEntity)
        Mockito.verify(storyDao).insertStories(storyEntity)
    }

    @Test
    fun `when delete Story List, then Success Delete from Database`() = runTest {
        repository.clearStoriesDb()
        Mockito.verify(storyDao).clearStories()
    }

    @Test
    fun `when user Get Story List, then return Success Get Data from Database`() = runTest {
        val expectedResponse = DummyData.generateDummyStoryEntity()

        Mockito.`when`(storyDao.getStoriesNoLiveData()).thenReturn(expectedResponse)
        repository.getStoriesNoLiveData().let {
            assertNotNull(it)
            assertTrue(it.isNotEmpty())
            assertEquals(expectedResponse.size, it.size)
            assertEquals(expectedResponse, it)
        }
        Mockito.verify(storyDao).getStoriesNoLiveData()
    }

    @Test
    fun `when user Get Story List, then return Empty List`() = runTest {
        val expectedResponse = mutableListOf<StoryEntity>()

        Mockito.`when`(storyDao.getStoriesNoLiveData()).thenReturn(expectedResponse)
        repository.getStoriesNoLiveData().let {
            assertNotNull(it)
            assertTrue(it.isEmpty())
        }
        Mockito.verify(storyDao).getStoriesNoLiveData()
    }

}