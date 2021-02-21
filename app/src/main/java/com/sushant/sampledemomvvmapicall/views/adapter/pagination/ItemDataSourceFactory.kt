package com.sushant.sampledemomvvmapicall.views.adapter.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.sushant.sampledemomvvmapicall.model.ProfilerItemData

class ItemDataSourceFactory : DataSource.Factory<Int, ProfilerItemData>() {

    val itemDataSource by lazy {
        ItemDataSource()
    }

    override fun create(): DataSource<Int, ProfilerItemData> {
        return itemDataSource
    }
    public fun getLoadCallback() : MutableLiveData<PageLoadingCallBack>{
        return itemDataSource.getLoadCallback()
    }
}