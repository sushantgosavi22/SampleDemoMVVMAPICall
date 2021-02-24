package com.sushant.sampledemomvvmapicall.repositorys.feedrepo

import android.content.Context
import com.sushant.sampledemomvvmapicall.database.helper.DatabaseFailure
import com.sushant.sampledemomvvmapicall.database.helper.DatabaseSuccess
import com.sushant.sampledemomvvmapicall.database.provider.DatabaseProvider
import com.sushant.sampledemomvvmapicall.database.provider.IDatabaseProvider
import com.sushant.sampledemomvvmapicall.model.FeedItem
import com.sushant.sampledemomvvmapicall.model.FeedResponse
import com.sushant.sampledemomvvmapicall.service.provider.IServiceProvider
import com.sushant.sampledemomvvmapicall.service.provider.ServiceProvider
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver

class FeedRepository : IFeedRepository {
    var mIDatabaseProvider: IDatabaseProvider = DatabaseProvider
    var mIServiceProvider: IServiceProvider = ServiceProvider


    override fun getFeeds(context: Context, page: Int): Single<FeedResponse> {
        return mIDatabaseProvider.getFeedsFromDatabase(page)
            .flatMap {
                when (it) {
                    is DatabaseFailure -> getUsersResponse(context, page)
                    is DatabaseSuccess -> Single.just(it.data)
                }
            }
    }

    private fun getUsersResponse(context: Context, page: Int): Single<FeedResponse> {
        return mIServiceProvider.getFeeds(context, page).flatMap {
            it.data?.forEach {
                saveFeed(it).subscribe(object : DisposableSingleObserver<Boolean>() {
                        override fun onSuccess(t: Boolean) {}
                        override fun onError(e: Throwable) {}
                    })
            }
            Single.just(it)
        }
    }


    override fun saveFeed(data: FeedItem?): Single<Boolean> {
        return mIDatabaseProvider.saveFeed(data)
    }
}