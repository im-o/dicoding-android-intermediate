package com.rivaldy.id.dicoding.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rivaldy.id.core.data.datasource.local.pref.PreferenceRepositoryImpl
import com.rivaldy.id.dicoding.util.MainDispatcherRule
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
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var repository: PreferenceRepositoryImpl

    private lateinit var viewModel: MainViewModel

    private val expectedResponse = "sample-token"

    @Before
    fun setUp() {
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `when user save Token, then Success save Token`() = runTest {
        viewModel.setUserTokenPref(expectedResponse)
        Mockito.verify(repository).setUserTokenPref(expectedResponse)
    }

    @Test
    fun `when user get saved Token, then return Success get Token`() = runTest {
        Mockito.`when`(repository.getUserTokenPref()).thenReturn(expectedResponse)
        viewModel.getUserTokenPref().let {
            assertNotNull(it)
            assertTrue(it.isNotEmpty())
            assertEquals(expectedResponse, it)
        }
        Mockito.verify(repository).getUserTokenPref()
    }

    @Test
    fun `when user clear saved Token, then Success clear Token`() = runTest {
        viewModel.clearLoginInfo()
        Mockito.verify(repository).clearLoginInfo()
    }
}