package com.dicoding.storyapp.presentation.ui.dashboard.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.dicoding.storyapp.DataDummy
import com.dicoding.storyapp.MainDispatcherRule
import com.dicoding.storyapp.data.entity.Story
import com.dicoding.storyapp.data.repository.StoryRepository
import com.dicoding.storyapp.presentation.ui.dashboard.StoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    @Test
    fun `when Get Story Should Not Null and Return Data`() = runTest {
        val data = StoryPagingSource.snapshot(DataDummy.stories)
        val expectedStory = flowOf(data)
        Mockito.`when`(storyRepository.getPagingData()).thenReturn(expectedStory)

        val mainViewModel = HomeViewModel(storyRepository)
        val actualStory = mainViewModel.stories.first()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.STORY_COMPARATOR,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStory)

        assertNotNull(differ.snapshot())
        assertEquals(DataDummy.stories.size, differ.snapshot().size)
        assertEquals(DataDummy.stories[0], differ.snapshot()[0])
    }

    @Test
    fun `when Get Story Empty Should Return No Data`() = runTest {
        val data = PagingData.from(emptyList<Story>())
        val expectedQuote = flowOf(data)
        Mockito.`when`(storyRepository.getPagingData()).thenReturn(expectedQuote)

        val mainViewModel = HomeViewModel(storyRepository)
        val actualQuote = mainViewModel.stories.first()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.STORY_COMPARATOR,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )

        differ.submitData(actualQuote)
        assertEquals(0, differ.snapshot().size)
    }

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

    class StoryPagingSource : PagingSource<Int, LiveData<List<Story>>>() {

        companion object {
            fun snapshot(items: List<Story>): PagingData<Story> {
                return PagingData.from(items)
            }
        }

        override fun getRefreshKey(state: PagingState<Int, LiveData<List<Story>>>): Int = 0

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<Story>>> {
            return LoadResult.Page(emptyList(), 0, 1)
        }
    }
}