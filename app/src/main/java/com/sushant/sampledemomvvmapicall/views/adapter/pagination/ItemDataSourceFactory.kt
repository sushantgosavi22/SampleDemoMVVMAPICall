package com.sushant.sampledemomvvmapicall.views.adapter.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.sushant.sampledemomvvmapicall.model.ListItemData

class ItemDataSourceFactory : DataSource.Factory<Int, ListItemData>() {

    val itemDataSource by lazy {
        ItemDataSource()
    }

    override fun create(): DataSource<Int, ListItemData> {
        return itemDataSource
    }
    public fun getLoadCallback() : MutableLiveData<PageLoadingCallBack>{
        return itemDataSource.getLoadCallback()
    }
}