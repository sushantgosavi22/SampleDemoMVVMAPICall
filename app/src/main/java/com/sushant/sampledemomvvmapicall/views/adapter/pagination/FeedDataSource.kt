package com.sushant.sampledemomvvmapicall.views.adapter.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.model.FeedItem

class FeedDataSource : PageKeyedDataSource<Int, FeedItem>() {
    val loadCall = MutableLiveData<PageLoadingCallBack>()
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, FeedItem>) {
        loadCall.postValue(LoadInitial(Utils.FIRST_PAGE, callback))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, FeedItem>) {
        loadCall.postValue(LoadBefore(params.key,callback))
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, FeedItem>) {
        loadCall.postValue(LoadAfter(params.key,callback))
    }
    companion object {
        const val PAGE_SIZE = 10
    }
}