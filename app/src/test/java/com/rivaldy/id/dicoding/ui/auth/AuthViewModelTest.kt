package com.rivaldy.id.dicoding.ui.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rivaldy.id.core.data.datasource.remote.rest.RestApiRepositoryImpl
import com.rivaldy.id.core.data.model.dummy.DummyData
import com.rivaldy.id.core.data.network.DataResource
import com.rivaldy.id.dicoding.util.MainDispatcherRule
import com.rivaldy.id.dicoding.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/** Created by github.com/im-o on 10/22/2022.  */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AuthViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var repository: RestApiRepositoryImpl

    private lateinit var viewModel: AuthViewModel

    @Before
    fun setUp() {
        viewModel = AuthViewModel(repository)
    }

    @Test
    fun `when user register, then return Success`() = runTest {
        val request = DummyData.dummyRegisterRequest()
        val expectedResponse = DummyData.dummyRegisterSuccess()

        `when`(repository.registerUserApiCall(request)).thenReturn(expectedResponse)
        viewModel.registerUserApiCall(request)

        viewModel.registerUser.getOrAwaitValue().let {
            Assert.assertNotNull(it)
            Assert.assertTrue(it is DataResource.Success)
            Assert.assertEquals(expectedResponse, it)
        }
        Mockito.verify(repository).registerUserApiCall(request)
    }

    @Test
    fun `when user register, then return Failure`() = runTest {
        val request = DummyData.dummyRegisterRequest()
        val expectedResponse = DummyData.dummyRegisterFailure()

        `when`(repository.registerUserApiCall(request)).thenReturn(expectedResponse)
        viewModel.registerUserApiCall(request)

        viewModel.registerUser.getOrAwaitValue().let {
            Assert.assertNotNull(it)
            Assert.assertTrue(it is DataResource.Failure)
            Assert.assertEquals(expectedResponse, it)
        }
        Mockito.verify(repository).registerUserApiCall(request)
    }

    @Test
    fun `when user login, then return Success`() = runTest {
        val request = DummyData.dummyLoginRequest()
        val expectedResponse = DummyData.dummyLoginSuccess()

        `when`(repository.loginUserApiCall(request)).thenReturn(expectedResponse)
        viewModel.loginUserApiCall(request)

        viewModel.loginUser.getOrAwaitValue().let {
            Assert.assertNotNull(it)
            Assert.assertTrue(it is DataResource.Success)
            Assert.assertEquals(expectedResponse, it)
        }
        Mockito.verify(repository).loginUserApiCall(request)
    }

    @Test
    fun `when user login, then return Failure`() = runTest {
        val request = DummyData.dummyLoginRequest()
        val expectedResponse = DummyData.dummyLoginFailure()

        `when`(repository.loginUserApiCall(request)).thenReturn(expectedResponse)
        viewModel.loginUserApiCall(request)

        viewModel.loginUser.getOrAwaitValue().let {
            Assert.assertNotNull(it)
            Assert.assertTrue(it is DataResource.Failure)
            Assert.assertEquals(expectedResponse, it)
        }
        Mockito.verify(repository).loginUserApiCall(request)
    }
}