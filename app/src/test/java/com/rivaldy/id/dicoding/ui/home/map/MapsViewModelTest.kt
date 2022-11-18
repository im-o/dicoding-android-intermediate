package com.rivaldy.id.dicoding.ui.home.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rivaldy.id.core.data.datasource.remote.rest.RestApiRepositoryImpl
import com.rivaldy.id.core.data.model.dummy.DummyData
import com.rivaldy.id.core.data.network.DataResource
import com.rivaldy.id.core.utils.UtilConstants.DEFAULT_LIMIT_PAGE
import com.rivaldy.id.core.utils.UtilConstants.ONLY_LOCATION_TYPE
import com.rivaldy.id.dicoding.util.MainDispatcherRule
import com.rivaldy.id.dicoding.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/** Created by github.com/im-o on 11/1/2022.  */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var repository: RestApiRepositoryImpl

    private lateinit var viewModel: MapsViewModel

    @Before
    fun setUp() {
        viewModel = MapsViewModel(repository)
    }

    @Test
    fun `when user Load Story, then return Story List`() = runTest {
        val expectedResponse = DataResource.Success(DummyData.generateDummyUserStoryResponse())

        Mockito.`when`(repository.getStoriesApiCall(size = DEFAULT_LIMIT_PAGE, locationType = ONLY_LOCATION_TYPE, page = null)).thenReturn(expectedResponse)
        viewModel.getStoriesApiCall(size = DEFAULT_LIMIT_PAGE, locationType = ONLY_LOCATION_TYPE)

        viewModel.userStories.getOrAwaitValue().let {
            assertNotNull(it)
            assertTrue(it is DataResource.Success)
            assertTrue((it as DataResource.Success).value.listStory?.isNotEmpty() == true)
            assertEquals(expectedResponse, it)
        }
        Mockito.verify(repository).getStoriesApiCall(size = DEFAULT_LIMIT_PAGE, locationType = ONLY_LOCATION_TYPE, page = null)
    }

    @Test
    fun `when user Load Story, then return Empty List`() = runTest {
        val expectedResponse = DataResource.Success(DummyData.generateDummyUserStoryEmpty())

        Mockito.`when`(repository.getStoriesApiCall(size = DEFAULT_LIMIT_PAGE, locationType = ONLY_LOCATION_TYPE, page = null)).thenReturn(expectedResponse)
        viewModel.getStoriesApiCall(size = DEFAULT_LIMIT_PAGE, locationType = ONLY_LOCATION_TYPE)

        viewModel.userStories.getOrAwaitValue().let {
            assertNotNull(it)
            assertTrue(it is DataResource.Success)
            assertTrue((it as DataResource.Success).value.listStory?.isEmpty() == true)
            assertEquals(expectedResponse, it)
        }
        Mockito.verify(repository).getStoriesApiCall(size = DEFAULT_LIMIT_PAGE, locationType = ONLY_LOCATION_TYPE, page = null)
    }
}