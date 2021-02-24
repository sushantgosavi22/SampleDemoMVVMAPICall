package com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.model.FeedItem
import com.sushant.sampledemomvvmapicall.model.FeedResponse
import com.sushant.sampledemomvvmapicall.repositorys.feedrepo.FeedRepository
import com.sushant.sampledemomvvmapicall.repositorys.feedrepo.IFeedRepository
import com.sushant.sampledemomvvmapicall.service.model.ApiResponse
import com.sushant.sampledemomvvmapicall.views.adapter.pagination.*
import com.sushant.sampledemomvvmapicall.views.base.BaseListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

class DashboardViewModel(application: Application) : BaseListViewModel<FeedItem>(application) {
    var mIFeedRepository: IFeedRepository = FeedRepository()
    var mApiResponse = MutableLiveData<ApiResponse<FeedResponse>>()
    private val itemDataSourceFactory by lazy {
        FeedDataSourceFactory()
    }

    private fun getFeeds(forcedRefresh: Boolean = false,page: Int, callBack: PageLoadingCallBack? = null) {
        setCurrentPageLoading(true)
        val pageToRequest = if (forcedRefresh) Utils.FIRST_PAGE else page
        mIFeedRepository.getFeeds(getApplication(), pageToRequest)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { disposable -> mApiResponse.value = ApiResponse.loading() }
            .doFinally { setCurrentPageLoading(false) }
            .subscribe(object : DisposableSingleObserver<FeedResponse>() {
                override fun onSuccess(t: FeedResponse) {
                    if (forcedRefresh) resetPaging()
                    if (pageToRequest == Utils.FIRST_PAGE) {
                        mApiResponse.value = ApiResponse.clearListAndHideError()
                    }
                    handleRefreshCallBack(callBack, t,pageToRequest)
                    mApiResponse.value = ApiResponse.success(t)
                    incrementPageCount()
                }

                override fun onError(e: Throwable) {
                    if (pageToRequest == Utils.FIRST_PAGE) {
                        mApiResponse.value = ApiResponse.emptyList()
                    }
                    mApiResponse.value = ApiResponse.error(e)
                }
            })
    }

    fun handleRefreshCallBack(callBack: PageLoadingCallBack?, response: FeedResponse, pageToRequest : Int) {
        try {
            callBack?.let {
                response.data?.let { list ->
                    when (it) {
                        is LoadInitial -> {
                            if (pageToRequest == Utils.FIRST_PAGE ) {
                                it.callback.onResult(list, null, callBack.pageValue+1)
                            }else{
                                it.callback.onResult(list, null, callBack.pageValue + 1)
                            }
                        }
                        is LoadBefore -> it.callback.onResult(list, if (callBack.pageValue > 1) callBack.pageValue - 1 else 1)
                        is LoadAfter -> it.callback.onResult(list, callBack.pageValue + 1)
                    }
                }
            }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    override fun onRefresh() {
        onShowLoading()
        resetPaging()
        callPaginatedApi()
    }

    private val loadObserver = Observer<PageLoadingCallBack> {
        val refresh = it is LoadInitial
        getFeeds(refresh,it.pageValue,it)
    }

    var itemPagedList : LiveData<PagedList<FeedItem>>? = null
    fun callPaginatedApi() {
        val config: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Utils.PAGE_SIZE)
            .build()
        itemPagedList = LivePagedListBuilder(itemDataSourceFactory, config).build()

        getLoadCall().observeForever(loadObserver)
    }

    private fun getLoadCall(): MutableLiveData<PageLoadingCallBack> {
        return itemDataSourceFactory.itemDataSource.loadCall
    }

    fun getPagedList():LiveData<PagedList<FeedItem>>? {
        return itemPagedList
    }

    fun getUserApiResponse(): MutableLiveData<ApiResponse<FeedResponse>> {
        return mApiResponse
    }

    override fun onCleared() {
        getLoadCall().removeObserver(loadObserver)
        super.onCleared()
    }

}

