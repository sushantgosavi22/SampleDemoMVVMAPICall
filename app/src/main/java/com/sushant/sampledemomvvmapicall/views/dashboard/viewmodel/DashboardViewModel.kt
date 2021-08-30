package com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.model.FeedResponse
import com.sushant.sampledemomvvmapicall.repositorys.feedrepo.IFeedRepository
import com.sushant.sampledemomvvmapicall.service.model.ApiResponse
import com.sushant.sampledemomvvmapicall.views.base.BaseViewModel
import io.reactivex.observers.DisposableSingleObserver

class DashboardViewModel(application: Application,private val mIFeedRepository : IFeedRepository, private val savedStateHandle: SavedStateHandle) : BaseViewModel(application) {
    private var mApiResponse : MutableLiveData<ApiResponse<FeedResponse>> = getPersistedFeedResponse()
    val mApiResponseTest: LiveData<ApiResponse<FeedResponse>> =mApiResponse
    fun getFeeds() {
        mIFeedRepository.getFeeds()
            .doOnSubscribe {
                onShowLoading()
                setFeedResponse(ApiResponse.loading())
                setFeedResponse(ApiResponse.clearListAndHideError())
            }
            .subscribe(object : DisposableSingleObserver<FeedResponse>() {
                override fun onSuccess(t: FeedResponse) {
                    setFeedResponse(ApiResponse.success(t))
                }

                override fun onError(e: Throwable) {
                    setFeedResponse(ApiResponse.emptyList())
                    setFeedResponse(ApiResponse.error(e))
                }
            })
    }


    fun onRefresh() {
        resetPageCount()
        onShowLoading()
        getFeeds()
    }

    fun getUserApiResponse(): MutableLiveData<ApiResponse<FeedResponse>> {
        return mApiResponse
    }

    override fun onCleared() {
        super.onCleared()
    }


    private fun setFeedResponse(response: ApiResponse<FeedResponse>) =savedStateHandle.set(Utils.RESPONSE, response)
    private fun getPersistedFeedResponse() : MutableLiveData<ApiResponse<FeedResponse>> =savedStateHandle.getLiveData(Utils.RESPONSE)
    fun isPersistedAvailable() :LiveData<Boolean> = savedStateHandle.getLiveData<Boolean>(Utils.PERSISTED,false)
    fun setPersisted(boolean: Boolean) = savedStateHandle.set(Utils.PERSISTED,boolean)

}