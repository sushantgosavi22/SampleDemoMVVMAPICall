package com.sushant.sampledemomvvmapicall.service.provider

import android.content.Context
import com.sushant.sampledemomvvmapicall.model.FeedResponse
import io.reactivex.Single

interface IServiceProvider {
    fun getFeeds(context: Context, page: Int): Single<FeedResponse>
}