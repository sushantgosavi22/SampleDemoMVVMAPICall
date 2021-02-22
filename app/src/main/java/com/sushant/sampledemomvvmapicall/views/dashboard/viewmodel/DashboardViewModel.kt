package com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.model.FeedResponse
import com.sushant.sampledemomvvmapicall.repositorys.feedrepo.IFeedRepository
import com.sushant.sampledemomvvmapicall.repositorys.feedrepo.FeedRepository
import com.sushant.sampledemomvvmapicall.service.model.ApiResponse
import com.sushant.sampledemomvvmapicall.views.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

class DashboardViewModel(private val mIFeedRepository : IFeedRepository, application: Application) : BaseViewModel(application) {
    var mApiResponse = MutableLiveData<ApiResponse<FeedResponse>>()
    val mApiResponseTest: LiveData<ApiResponse<FeedResponse>> =mApiResponse
    fun getUsers(page : Int = Utils.FIRST_PAGE) {
        mIFeedRepository.getFeeds(page)
            .doOnSubscribe { disposable ->
                onShowLoading()
                mApiResponse.value = ApiResponse.loading()
            }
            .subscribe(object : DisposableSingleObserver<FeedResponse>() {
                override fun onSuccess(t: FeedResponse) {
                    if (page == Utils.FIRST_PAGE) {
                        mApiResponse.value = ApiResponse.clearListAndHideError()
                    }
                    mApiResponse.value = ApiResponse.success(t)
                }

                override fun onError(e: Throwable) {
                    if (page == Utils.FIRST_PAGE) {
                        mApiResponse.value = ApiResponse.emptyList()
                    }
                    mApiResponse.value = ApiResponse.error(e)
                }
            })
    }


    fun onRefresh() {
        resetPageCount()
        onShowLoading()
        getUsers()
    }

    fun getUserApiResponse(): MutableLiveData<ApiResponse<FeedResponse>> {
        return mApiResponse
    }

    override fun onCleared() {
        super.onCleared()
    }

    class DashboardViewModelFactory(private var repo: IFeedRepository,private var app: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(IFeedRepository::class.java, Application::class.java).newInstance(repo, app)
        }
    }
}