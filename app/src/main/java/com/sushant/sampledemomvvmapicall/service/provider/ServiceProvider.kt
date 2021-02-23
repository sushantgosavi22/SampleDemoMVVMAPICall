package com.sushant.sampledemomvvmapicall.service.provider

import android.content.Context
import com.sushant.sampledemomvvmapicall.model.FeedResponse
import com.sushant.sampledemomvvmapicall.service.clients.APIClient.apiServices
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

object ServiceProvider : IServiceProvider {
    override fun getFeeds(context: Context, page: Int): Single<FeedResponse> {
        return apiServices.getFeeds(page).subscribeOn(Schedulers.io())
    }
}