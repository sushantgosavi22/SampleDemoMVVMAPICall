package com.sushant.sampledemomvvmapicall.service.provider

import com.sushant.sampledemomvvmapicall.model.FeedResponse
import com.sushant.sampledemomvvmapicall.service.clients.APIClient.apiServices
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

object ServiceProvider : IServiceProvider {
    override fun getFeeds(limit: Int, offset: Int): Single<FeedResponse> {
        return apiServices.getFeeds(limit,offset).subscribeOn(Schedulers.io())
    }
}