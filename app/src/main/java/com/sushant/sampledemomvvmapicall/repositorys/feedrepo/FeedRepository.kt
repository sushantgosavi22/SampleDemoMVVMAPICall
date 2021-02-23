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
        return mIDatabaseProvider.getFeedsFromDatabase()
            .flatMap {
                /**
                 * Here If you want to test pagination then we I need to allow only API call
                 * because As per problem statment
                 * [ if records are not present fetch record from above URL and. store in DB, also display records on the collection view]
                 * if we store in db next consequence call got record from DB and API not call
                 * So I have set flag for pagination that we call API only to test
                 */
                if(isPaginationOn.not()){
                    when (it) {
                        is DatabaseFailure -> getUsersResponse(context, page)
                        is DatabaseSuccess -> Single.just(it.data)
                    }
                }else{
                    getUsersResponse(context, page)
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