package com.dicoding.newsapp.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.dicoding.newsapp.data.NewsRepository
import com.dicoding.newsapp.data.Result
import com.dicoding.newsapp.data.local.entity.NewsEntity
import com.dicoding.newsapp.utils.DataDummy
import com.dicoding.newsapp.utils.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var newsRepository: NewsRepository
    private lateinit var newsViewModel: NewsViewModel
    private val dummyNews = DataDummy.news

    @Before
    fun setup() {
        newsViewModel = NewsViewModel(newsRepository)
    }

    @Test
    fun `when Get HeadlineNews should not null and return success`() {
        val expectedNews = MutableLiveData<Result<List<NewsEntity>>>()
        expectedNews.value = Result.Success(dummyNews)
        `when`(newsRepository.getHeadlineNews()).thenReturn(expectedNews)

        val actualNews = newsViewModel.getHeadlineNews().getOrAwaitValue()
        Mockito.verify(newsRepository).getHeadlineNews()
        assertNotNull(actualNews)
        assertTrue(actualNews is Result.Success)
        assertEquals(dummyNews.size, (actualNews as Result.Success).data.size)
    }

    @Test
    fun `when Network Error Should Return Error`() {
        val headlineNews = MutableLiveData<Result<List<NewsEntity>>>()
        headlineNews.value = Result.Error("Error")
        `when`(newsRepository.getHeadlineNews()).thenReturn(headlineNews)
        val actualNews = newsViewModel.getHeadlineNews().getOrAwaitValue()
        Mockito.verify(newsRepository).getHeadlineNews()
        assertNotNull(actualNews)
        assertTrue(actualNews is Result.Error)
    }
}