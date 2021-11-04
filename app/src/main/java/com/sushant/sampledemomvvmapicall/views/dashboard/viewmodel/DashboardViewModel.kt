package com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.model.FeedResponse
import com.sushant.sampledemomvvmapicall.repositorys.feedrepo.IFeedRepository
import com.sushant.sampledemomvvmapicall.service.model.ApiResponse
import com.sushant.sampledemomvvmapicall.views.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

class DashboardViewModel(application: Application,private val mIFeedRepository : IFeedRepository, private val savedStateHandle: SavedStateHandle) : BaseViewModel(application) {
    var apiResponse : MutableLiveData<ApiResponse<FeedResponse>> = getPersistedFeedResponse()
    val apiResponseTest: LiveData<ApiResponse<FeedResponse>> =apiResponse
    private val compositeDisposable = CompositeDisposable()

    fun getFeeds(offset: Int = Utils.FEED_DEFAULT_OFFSET) {
        compositeDisposable += mIFeedRepository.getFeeds(Utils.FEED_LIMIT, offset)
            .doOnSubscribe {
                onShowLoading()
                setFeedResponse(ApiResponse.loading())
            }
            .subscribeBy(
                onError = {
                    setFeedResponse(ApiResponse.error(it))
                },
                onSuccess = {
                    if (offset == Utils.FEED_DEFAULT_OFFSET) setFeedResponse(ApiResponse.clearListAndHideError())
                    setFeedResponse(ApiResponse.success(it))
                }
            )
    }


    fun getFeedApiResponse(): MutableLiveData<ApiResponse<FeedResponse>> {
        return apiResponse
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


    private fun setFeedResponse(response: ApiResponse<FeedResponse>) =savedStateHandle.set(Utils.RESPONSE, response)
    private fun getPersistedFeedResponse() : MutableLiveData<ApiResponse<FeedResponse>> =savedStateHandle.getLiveData(Utils.RESPONSE)
    fun isPersistedAvailable() :LiveData<Boolean> = savedStateHandle.getLiveData<Boolean>(Utils.PERSISTED,false)
    fun setPersisted(boolean: Boolean) = savedStateHandle.set(Utils.PERSISTED,boolean)

    class DashboardViewModelFactory(private var app: Application,private var repo: IFeedRepository,private var mSavedStateHandle : SavedStateHandle) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(Application::class.java,IFeedRepository::class.java,SavedStateHandle::class.java).newInstance(app,repo,mSavedStateHandle)
        }
    }
}