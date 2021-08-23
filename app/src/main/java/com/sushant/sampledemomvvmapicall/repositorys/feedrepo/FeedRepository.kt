package com.sushant.sampledemomvvmapicall.repositorys.feedrepo

import com.sushant.sampledemomvvmapicall.service.provider.IServiceProvider
import com.sushant.sampledemomvvmapicall.service.provider.ServiceProvider
import com.sushant.sampledemomvvmapicall.service.socket.SocketResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

open class FeedRepository : IFeedRepository {
    var mIServiceProvider: IServiceProvider = ServiceProvider

    override fun getFeeds(page: Int): Observable<SocketResponse> {
        return mIServiceProvider.getFeeds(page).observeOn(AndroidSchedulers.mainThread())
    }

}