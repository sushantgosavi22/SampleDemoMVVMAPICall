package com.sushant.sampledemomvvmapicall.repositorys.feedrepo

import com.sushant.sampledemomvvmapicall.model.FeedResponse
import com.sushant.sampledemomvvmapicall.service.provider.IServiceProvider
import com.sushant.sampledemomvvmapicall.service.provider.ServiceProvider
import io.reactivex.Observable

open class FeedRepository : IFeedRepository {

    var mIServiceProvider: IServiceProvider = ServiceProvider

    override fun getFeeds(page: Int): Observable<FeedResponse> {
        return mIServiceProvider.getFeeds(page)
    }

}