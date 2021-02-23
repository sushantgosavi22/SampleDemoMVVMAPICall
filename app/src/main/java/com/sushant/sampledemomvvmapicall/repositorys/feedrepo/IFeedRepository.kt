package com.sushant.sampledemomvvmapicall.repositorys.feedrepo

import android.content.Context
import com.sushant.sampledemomvvmapicall.model.FeedItem
import com.sushant.sampledemomvvmapicall.model.FeedResponse
import io.reactivex.Single

interface IFeedRepository {
    fun getFeeds(context: Context, page: Int,isPaginationOn: Boolean = false) : Single<FeedResponse>
    fun saveFeed(data : FeedItem?) : Single<Boolean>
}