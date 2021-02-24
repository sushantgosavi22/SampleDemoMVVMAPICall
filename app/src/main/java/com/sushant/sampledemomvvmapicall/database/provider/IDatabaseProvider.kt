package com.sushant.sampledemomvvmapicall.database.provider

import com.sushant.sampledemomvvmapicall.database.helper.DatabaseResult
import com.sushant.sampledemomvvmapicall.model.FeedItem
import com.sushant.sampledemomvvmapicall.model.FeedResponse
import io.reactivex.Single

interface IDatabaseProvider {
    fun saveFeed(model: FeedItem?) : Single<Boolean>
    fun getFeedsFromDatabase( page: Int) : Single<DatabaseResult<FeedResponse>>
}