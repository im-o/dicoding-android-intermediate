package com.rivaldy.id.core.data.datasource.remote.rest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rivaldy.id.core.data.model.dummy.DummyData
import com.rivaldy.id.core.data.model.remote.DefaultResponse
import com.rivaldy.id.core.data.model.remote.login.LoginResponse
import com.rivaldy.id.core.data.model.remote.register.RegisterResponse
import com.rivaldy.id.core.data.network.DataResource
import com.rivaldy.id.core.util.MainDispatcherRule
import com.rivaldy.id.core.utils.UtilConstants.DEFAULT_LIMIT_PAGE
import com.rivaldy.id.core.utils.UtilConstants.ONLY_LOCATION_TYPE
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
class RestApiRepositoryImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var apiService = mock(ApiService::class.java)

    private lateinit var repository: RestApiRepositoryImpl

    @Before
    fun setup() {
        repository = RestApiRepositoryImpl(apiService)
    }

    @Test
    fun `when user register, then return Success`() = runTest {
        val request = DummyData.dummyRegisterRequest()
        val expectedResponse = DummyData.dummyRegisterSuccess()

        Mockito.`when`(apiService.registerUser(request.name ?: "", request.email ?: "", request.password ?: "")).thenReturn(expectedResponse.value)
        repository.registerUserApiCall(request).let {
            assertNotNull(it)
            assertTrue(it is DataResource.Success)
            assertEquals(expectedResponse, it)
        }
        Mockito.verify(apiService).registerUser(request.name ?: "", request.email ?: "", request.password ?: "")
    }

    @Test
    fun `when user register, then return Failure`() = runTest {
        val request = DummyData.dummyRegisterRequest()
        val expectedResponse = RegisterResponse(true, DummyData.dummyRegisterFailure().otherMessage)

        Mockito.`when`(apiService.registerUser(request.name ?: "", request.email ?: "", request.password ?: "")).thenReturn(expectedResponse)
        repository.registerUserApiCall(request).let {
            assertNotNull(it)
            assertEquals(expectedResponse, (it as DataResource.Success).value)
        }
        Mockito.verify(apiService).registerUser(request.name ?: "", request.email ?: "", request.password ?: "")
    }

    @Test
    fun `when user login, then return Success`() = runTest {
        val request = DummyData.dummyLoginRequest()
        val expectedResponse = DummyData.dummyLoginSuccess()

        Mockito.`when`(apiService.loginUser(request.email ?: "", request.password ?: "")).thenReturn(expectedResponse.value)
        repository.loginUserApiCall(request).let {
            assertNotNull(it)
            assertTrue(it is DataResource.Success)
            assertEquals(expectedResponse, it)
        }
        Mockito.verify(apiService).loginUser(request.email ?: "", request.password ?: "")
    }

    @Test
    fun `when user login, then return Failure`() = runTest {
        val request = DummyData.dummyLoginRequest()
        val expectedResponse = LoginResponse(true, null, DummyData.dummyLoginFailure().otherMessage)

        Mockito.`when`(apiService.loginUser(request.email ?: "", request.password ?: "")).thenReturn(expectedResponse)
        repository.loginUserApiCall(request).let {
            assertNotNull(it)
            assertEquals(expectedResponse, (it as DataResource.Success).value)
        }
        Mockito.verify(apiService).loginUser(request.email ?: "", request.password ?: "")
    }

    @Test
    fun `when user Load Story, then return Story List`() = runTest {
        val expectedResponse = DataResource.Success(DummyData.generateDummyUserStoryResponse())

        Mockito.`when`(apiService.getStories(size = DEFAULT_LIMIT_PAGE, location = ONLY_LOCATION_TYPE, page = null)).thenReturn(expectedResponse.value)
        repository.getStoriesApiCall(size = DEFAULT_LIMIT_PAGE, locationType = ONLY_LOCATION_TYPE, page = null).let {
            assertNotNull(it)
            assertTrue(it is DataResource.Success)
            assertTrue((it as DataResource.Success).value.listStory?.isNotEmpty() == true)
            assertEquals(expectedResponse, it)
        }
        Mockito.verify(apiService).getStories(size = DEFAULT_LIMIT_PAGE, location = ONLY_LOCATION_TYPE, page = null)
    }

    @Test
    fun `when user Load Story, then return Empty List`() = runTest {
        val expectedResponse = DataResource.Success(DummyData.generateDummyUserStoryEmpty())

        Mockito.`when`(apiService.getStories(size = DEFAULT_LIMIT_PAGE, location = ONLY_LOCATION_TYPE, page = null)).thenReturn(expectedResponse.value)
        repository.getStoriesApiCall(size = DEFAULT_LIMIT_PAGE, locationType = ONLY_LOCATION_TYPE, page = null).let {
            assertNotNull(it)
            assertTrue(it is DataResource.Success)
            assertTrue((it as DataResource.Success).value.listStory?.isEmpty() == true)
            assertEquals(expectedResponse, it)
        }
        Mockito.verify(apiService).getStories(size = DEFAULT_LIMIT_PAGE, location = ONLY_LOCATION_TYPE, page = null)
    }

    @Test
    fun `when user add story, then return Success`() = runTest {
        val descriptionBody = DummyData.dummyDescriptionBody
        val imageMultipart = DummyData.dummyImageMultipart
        val expectedResponse = DummyData.dummyAddStorySuccess()

        Mockito.`when`(apiService.addStory(descriptionBody, imageMultipart, null, null)).thenReturn(expectedResponse.value)
        repository.addStoryApiCall(descriptionBody, imageMultipart, null, null).let {
            assertNotNull(it)
            assertTrue(it is DataResource.Success)
            assertEquals(expectedResponse, it)
        }
        Mockito.verify(apiService).addStory(descriptionBody, imageMultipart, null, null)
    }

    @Test
    fun `when user add story, then return Failure`() = runTest {
        val descriptionBody = DummyData.dummyDescriptionBody
        val imageMultipart = DummyData.dummyImageMultipart
        val expectedResponse = DefaultResponse(false, DummyData.dummyAddStoryFailure().otherMessage)

        Mockito.`when`(apiService.addStory(descriptionBody, imageMultipart, null, null)).thenReturn(expectedResponse)
        repository.addStoryApiCall(descriptionBody, imageMultipart, null, null).let {
            assertNotNull(it)
            assertEquals(expectedResponse, (it as DataResource.Success).value)
        }
        Mockito.verify(apiService).addStory(descriptionBody, imageMultipart, null, null)
    }
}