package com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.sushant.sampledemomvvmapicall.model.FeedItem
import com.sushant.sampledemomvvmapicall.model.FeedResponse
import com.sushant.sampledemomvvmapicall.repositorys.feedrepo.FeedRepository
import com.sushant.sampledemomvvmapicall.service.model.ApiResponse
import io.reactivex.Single
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DashboardViewModelTest : TestCase() {
    private val page =1
    private lateinit var feedResponse: FeedResponse
    private lateinit var list: ArrayList<FeedItem>
    private lateinit var viewModel: DashboardViewModel
    private lateinit var apiResponseObserver : Observer<ApiResponse<FeedResponse>>
    @Mock
    private lateinit var context: Application
    @Mock
    private lateinit var repository: FeedRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
        repository = Mockito.mock(FeedRepository::class.java)
        viewModel = DashboardViewModel(context,repository, SavedStateHandle())
        apiResponseObserver = Mockito.mock(Observer::class.java) as Observer<ApiResponse<FeedResponse>>
        mockRequiredData()
    }


    @Test
    fun testGetFeedSuccessScenario(){
        viewModel.mApiResponseTest.observeForever(apiResponseObserver)
        Mockito.`when`(repository.getFeeds()).thenReturn(Single.just(feedResponse))
        viewModel.getFeeds()

        Mockito.verify(repository, Mockito.times(1)).getFeeds()
        val response = viewModel.mApiResponseTest.value?.response as FeedResponse
        Assert.assertEquals(response.rows?.size, 3)
    }


    @Test(expected = Exception::class)
    fun testGetFeedFailedScenario(){
        viewModel.mApiResponseTest.observeForever(apiResponseObserver)
        Mockito.doThrow(Throwable()).`when`(repository).getFeeds()
        viewModel.getFeeds()
        Assert.assertNull(viewModel.mApiResponseTest.value?.response)
    }



    @Test
    fun testOnRefreshScenario(){
        viewModel.mApiResponseTest.observeForever(apiResponseObserver)
        Mockito.`when`(repository.getFeeds()).thenReturn(Single.just(feedResponse))
        viewModel.onRefresh()

        Mockito.verify(repository, Mockito.times(1)).getFeeds()
        val response = viewModel.mApiResponseTest.value?.response as FeedResponse
        Assert.assertEquals(response.rows?.size, 3)
    }


    private fun mockRequiredData() {
        list = ArrayList()
        list.add(FeedItem().apply {
                this.imageHref=""
                this.type=""
                this.setup=""
            }
        )
        list.add(
            FeedItem().apply {
                this.imageHref=""
                this.type=""
                this.setup=""
            }
        )
        list.add(
            FeedItem().apply {
                this.imageHref=""
                this.type=""
                this.setup=""
            }
        )
        feedResponse = FeedResponse().apply {
            rows =list
            title= "Dummy Title"
        }
    }

}