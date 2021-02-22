package com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.model.ProfilerResponse
import com.sushant.sampledemomvvmapicall.repositorys.feedrepo.IFeedRepository
import com.sushant.sampledemomvvmapicall.repositorys.feedrepo.FeedRepository
import com.sushant.sampledemomvvmapicall.service.model.ApiResponse
import com.sushant.sampledemomvvmapicall.views.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

class DashboardViewModel(application: Application) : BaseViewModel(application) {
    var mIFeedRepository: IFeedRepository = FeedRepository()
    var mApiResponse = MutableLiveData<ApiResponse<ProfilerResponse>>()

    fun getUsers(page : Int = Utils.FIRST_PAGE) {
        mIFeedRepository.getFeeds(page)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { disposable ->
                onShowLoading()
                mApiResponse.value = ApiResponse.loading()
            }
            .subscribe(object : DisposableSingleObserver<ProfilerResponse>() {
                override fun onSuccess(t: ProfilerResponse) {
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

    fun getUserApiResponse(): MutableLiveData<ApiResponse<ProfilerResponse>> {
        return mApiResponse
    }

    override fun onCleared() {
        super.onCleared()
    }

}