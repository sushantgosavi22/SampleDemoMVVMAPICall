package com.sushant.sampledemomvvmapicall.repositorys.feedrepo


import com.sushant.sampledemomvvmapicall.model.FeedResponse
import io.reactivex.Observable

interface IFeedRepository {
    fun getFeeds(page: Int): Observable<FeedResponse>
}