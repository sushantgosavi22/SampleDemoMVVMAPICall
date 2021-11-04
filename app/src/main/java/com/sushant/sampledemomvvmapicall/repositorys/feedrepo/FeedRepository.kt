package com.sushant.sampledemomvvmapicall.repositorys.feedrepo

import com.sushant.sampledemomvvmapicall.model.FeedResponse
import com.sushant.sampledemomvvmapicall.service.provider.IServiceProvider
import com.sushant.sampledemomvvmapicall.service.provider.ServiceProvider
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

open class FeedRepository : IFeedRepository {
    var mIServiceProvider: IServiceProvider = ServiceProvider

    override fun getFeeds(limit: Int, offset: Int): Single<FeedResponse> {
        return mIServiceProvider.getFeeds(limit, offset).observeOn(AndroidSchedulers.mainThread())
    }

}