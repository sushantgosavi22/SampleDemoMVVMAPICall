package com.sushant.sampledemomvvmapicall.views.adapter.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.sushant.sampledemomvvmapicall.model.FeedItem

class FeedDataSourceFactory : DataSource.Factory<Int, FeedItem>() {

    val itemDataSource by lazy { FeedDataSource() }

    override fun create(): DataSource<Int, FeedItem> {
        return itemDataSource
    }
}