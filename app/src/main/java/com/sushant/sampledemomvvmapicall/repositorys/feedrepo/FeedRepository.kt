package com.sushant.sampledemomvvmapicall.repositorys.feedrepo

import com.sushant.sampledemomvvmapicall.model.UserResponse
import com.sushant.sampledemomvvmapicall.service.provider.IServiceProvider
import com.sushant.sampledemomvvmapicall.service.provider.ServiceProvider
import io.reactivex.Single

class FeedRepository : IFeedRepository {
    var mIServiceProvider: IServiceProvider = ServiceProvider

    override fun getFeeds(page: Int): Single<UserResponse> {
        return mIServiceProvider.getFeeds(page)
    }

}