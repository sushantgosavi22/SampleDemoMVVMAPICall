package com.sushant.sampledemomvvmapicall.service.provider

import com.sushant.sampledemomvvmapicall.model.FeedItem
import com.sushant.sampledemomvvmapicall.model.FeedResponse
import com.sushant.sampledemomvvmapicall.service.clients.APIClient.apiServices
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

object ServiceProvider : IServiceProvider {
    override fun getFeeds(page: Int): Single<FeedResponse> {
        return apiServices.getFeeds(page).subscribeOn(Schedulers.io()).flatMap {
            val filteredList = it.rows?.filter {(it.description==null&&it.title==null&&it.imageHref==null ).not()}
            it.rows =ArrayList(filteredList )
            Single.just(it)
        }
    }
}