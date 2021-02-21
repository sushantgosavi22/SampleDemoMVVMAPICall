package com.sushant.sampledemomvvmapicall.views.adapter.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.model.ProfilerItemData

class ItemDataSource : PageKeyedDataSource<Int, ProfilerItemData>() {
    val loadCall = MutableLiveData<PageLoadingCallBack>()
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ProfilerItemData>) {
        loadCall.postValue(LoadInitial(Utils.FIRST_PAGE, callback))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ProfilerItemData>) {
        loadCall.postValue(LoadBefore(params.key,callback))
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ProfilerItemData>) {
        loadCall.postValue(LoadAfter(params.key,callback))
    }

    public fun getLoadCallback() : MutableLiveData<PageLoadingCallBack>{
        return loadCall
    }

    companion object {
        const val PAGE_SIZE = 10
    }
}