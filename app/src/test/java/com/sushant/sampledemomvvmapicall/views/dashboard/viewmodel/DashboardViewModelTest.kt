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
        viewModel.apiResponseTest.observeForever(apiResponseObserver)
        Mockito.`when`(repository.getFeeds(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Single.just(feedResponse))
        viewModel.getFeeds(page)

        Mockito.verify(repository, Mockito.times(1)).getFeeds(limit = Mockito.anyInt(),offset = Mockito.anyInt())
        val response = viewModel.apiResponseTest.value?.response as FeedResponse
        Assert.assertEquals(response.data?.size, 3)
    }


    @Test(expected = Exception::class)
    fun testGetFeedFailedScenario(){
        viewModel.apiResponseTest.observeForever(apiResponseObserver)
        Mockito.doThrow(Throwable()).`when`(repository).getFeeds(limit = 0,offset = page)
        viewModel.getFeeds(page)
        Assert.assertNull(viewModel.apiResponseTest.value?.response)
    }

    @Test
    fun setPersisted(){
        val shouldPersist = true
        val persistence = viewModel.isPersistedAvailable()
        assertFalse(persistence.value == true)
        viewModel.setPersisted(shouldPersist)
        assertTrue(persistence.value == true)
    }

    private fun mockRequiredData() {
        list = ArrayList()
        list.add(FeedItem().apply {
                this.imageHref=""
                this.title=""
                this.description=""
            }
        )
        list.add(
            FeedItem().apply {
                this.imageHref=""
                this.title=""
                this.description=""
            }
        )
        list.add(
            FeedItem().apply {
                this.imageHref=""
                this.title=""
                this.description=""
            }
        )
        feedResponse = FeedResponse().apply {
            data =list
            title= "Dummy Title"
        }
    }

}