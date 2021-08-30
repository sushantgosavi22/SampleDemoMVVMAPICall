package com.sushant.sampledemomvvmapicall.service.provider

import com.sushant.sampledemomvvmapicall.model.FeedResponse
import io.reactivex.Single

interface IServiceProvider {
    fun getFeeds(): Single<FeedResponse>
}