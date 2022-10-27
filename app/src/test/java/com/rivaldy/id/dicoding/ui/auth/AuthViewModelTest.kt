package com.rivaldy.id.dicoding.ui.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rivaldy.id.core.data.datasource.remote.rest.RestApiRepositoryImpl
import com.rivaldy.id.core.data.model.remote.register.RegisterRequest
import com.rivaldy.id.core.data.model.remote.register.RegisterResponse
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
        val registerRequest = RegisterRequest("rivaldy", "rival@gmail.com", "rival123")
        val expectedResponse = DataResource.Success(RegisterResponse(true, "Email is already taken"))

        `when`(repository.registerUserApiCall(registerRequest)).thenReturn(expectedResponse)
        viewModel.registerUserApiCall(registerRequest)
        assert(viewModel.registerUser.getOrAwaitValue() is DataResource.Success)
        Assert.assertEquals(expectedResponse, viewModel.registerUser.getOrAwaitValue())
        Mockito.verify(repository).registerUserApiCall(registerRequest)
    }
}