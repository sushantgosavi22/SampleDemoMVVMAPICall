package com.sushant.sampledemomvvmapicall.service.provider

import com.sushant.sampledemomvvmapicall.model.FeedResponse
import io.reactivex.Observable

interface IServiceProvider {
    fun getFeeds(page: Int): Observable<FeedResponse>
}