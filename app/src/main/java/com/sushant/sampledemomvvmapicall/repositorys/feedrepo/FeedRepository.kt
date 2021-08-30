package com.sushant.sampledemomvvmapicall.repositorys.feedrepo

import com.sushant.sampledemomvvmapicall.model.FeedResponse
import com.sushant.sampledemomvvmapicall.service.provider.IServiceProvider
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

open class FeedRepository(private var mIServiceProvider: IServiceProvider) : IFeedRepository {

    override fun getFeeds(): Single<FeedResponse> {
        return mIServiceProvider.getFeeds().observeOn(AndroidSchedulers.mainThread())
    }

}