package com.rivaldy.id.dicoding.ui.home.addstory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rivaldy.id.core.data.datasource.remote.rest.RestApiRepositoryImpl
import com.rivaldy.id.core.data.model.dummy.DummyData
import com.rivaldy.id.core.data.network.DataResource
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

/** Created by github.com/im-o on 10/31/2022.  */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var repository: RestApiRepositoryImpl

    private lateinit var viewModel: AddStoryViewModel

    @Before
    fun setUp() {
        viewModel = AddStoryViewModel(repository)
    }

    @Test
    fun `when user add story, then return Success`() = runTest {
        val descriptionBody = DummyData.dummyDescriptionBody
        val imageMultipart = DummyData.dummyImageMultipart
        val expectedResponse = DummyData.dummyAddStorySuccess()

        Mockito.`when`(repository.addStoryApiCall(descriptionBody, imageMultipart, null, null)).thenReturn(expectedResponse)
        viewModel.addStoryApiCall(descriptionBody, imageMultipart, null, null)

        viewModel.addStory.getOrAwaitValue().let {
            assertNotNull(it)
            assertTrue(it is DataResource.Success)
            assertEquals(expectedResponse, it)
        }
        Mockito.verify(repository).addStoryApiCall(descriptionBody, imageMultipart, null, null)
    }

    @Test
    fun `when user add story, then return Failure`() = runTest {
        val descriptionBody = DummyData.dummyDescriptionBody
        val imageMultipart = DummyData.dummyImageMultipart
        val expectedResponse = DummyData.dummyAddStoryFailure()

        Mockito.`when`(repository.addStoryApiCall(descriptionBody, imageMultipart, null, null)).thenReturn(expectedResponse)
        viewModel.addStoryApiCall(descriptionBody, imageMultipart, null, null)

        viewModel.addStory.getOrAwaitValue().let {
            assertNotNull(it)
            assertTrue(it is DataResource.Failure)
            assertEquals(expectedResponse, it)
        }
        Mockito.verify(repository).addStoryApiCall(descriptionBody, imageMultipart, null, null)
    }
}