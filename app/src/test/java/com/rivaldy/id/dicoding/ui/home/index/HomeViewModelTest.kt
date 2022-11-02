package com.rivaldy.id.dicoding.ui.home.index

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import androidx.recyclerview.widget.ListUpdateCallback
import com.rivaldy.id.core.data.model.dummy.DummyData
import com.rivaldy.id.core.data.model.local.db.StoryEntity
import com.rivaldy.id.core.data.paging.repository.StoryPagingRepository
import com.rivaldy.id.dicoding.util.MainDispatcherRule
import com.rivaldy.id.dicoding.util.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by github.com/im-o on 10/30/2022.
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyPagingRepository: StoryPagingRepository

    @Test
    fun `when Get Story List Should Not Null and Return Success`() = runTest {
        val dummyStory = DummyData.generateDummyStoryEntity()
        val data: PagingData<StoryEntity> = StoryPagingSource.snapshot(dummyStory)
        val expectedQuote = MutableLiveData<PagingData<StoryEntity>>()
        expectedQuote.value = data

        Mockito.`when`(storyPagingRepository.getStoriesPaging()).thenReturn(expectedQuote)
        val mainViewModel = HomeViewModel(storyPagingRepository)
        val actualQuote: PagingData<StoryEntity> = mainViewModel.getStoriesPagingApiCall().getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = HomeAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)

        assertNotNull(differ.snapshot())
        assertEquals(dummyStory, differ.snapshot())
        assertEquals(dummyStory.size, differ.snapshot().size)
        assertEquals(dummyStory[0].name, differ.snapshot()[0]?.name)
        Mockito.verify(storyPagingRepository).getStoriesPaging()
    }

    @Test
    fun `when Get Story List Should Be Empty and Return Success`() = runTest {
        val dummyStory = mutableListOf<StoryEntity>()
        val data: PagingData<StoryEntity> = StoryPagingSource.snapshot(dummyStory)
        val expectedQuote = MutableLiveData<PagingData<StoryEntity>>()
        expectedQuote.value = data

        Mockito.`when`(storyPagingRepository.getStoriesPaging()).thenReturn(expectedQuote)
        val mainViewModel = HomeViewModel(storyPagingRepository)
        val actualQuote: PagingData<StoryEntity> = mainViewModel.getStoriesPagingApiCall().getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = HomeAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)

        assertNotNull(differ.snapshot())
        assertEquals(dummyStory, differ.snapshot())
        assertEquals(dummyStory.isEmpty(), differ.snapshot().isEmpty())
        Mockito.verify(storyPagingRepository).getStoriesPaging()
    }
}

class StoryPagingSource : PagingSource<Int, LiveData<List<StoryEntity>>>() {
    companion object {
        fun snapshot(items: MutableList<StoryEntity>): PagingData<StoryEntity> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<StoryEntity>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<StoryEntity>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}