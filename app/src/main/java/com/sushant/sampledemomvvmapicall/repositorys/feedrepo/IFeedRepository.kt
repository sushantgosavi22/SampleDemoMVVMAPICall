package com.sushant.sampledemomvvmapicall.repositorys.feedrepo

import com.sushant.sampledemomvvmapicall.model.FeedResponse
import io.reactivex.Single

interface IFeedRepository {
    fun getFeeds(limit: Int, offset: Int) : Single<FeedResponse>
}