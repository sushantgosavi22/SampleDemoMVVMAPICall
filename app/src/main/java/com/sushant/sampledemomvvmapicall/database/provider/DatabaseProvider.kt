package com.sushant.sampledemomvvmapicall.database.provider

import android.os.Looper
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.application.App
import com.sushant.sampledemomvvmapicall.database.helper.*
import com.sushant.sampledemomvvmapicall.model.FeedItem
import com.sushant.sampledemomvvmapicall.model.FeedResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.realm.Realm

object DatabaseProvider : IDatabaseProvider {

    override fun saveFeed(model: FeedItem?): Single<Boolean> {
        if (model == null) return Single.create<Boolean> { it.onError(Throwable()) }
        return Single.just(model)
            .observeOn(AndroidSchedulers.from(Looper.getMainLooper()))
            .flatMap {
                val realm = Realm.getDefaultInstance()
                Single.create<Boolean> { emitter ->
                    realm.executeTransactionAsync({ realm ->
                        it.id = Math.random().toInt()
                        realm.copyToRealm(model)
                    }, {
                        emitter.onSuccess(true)
                    }) {
                        emitter.onError(it)
                    }
                }
            }
    }

    override fun getFeedsFromDatabase(): Single<DatabaseResult<FeedResponse>> {
        return Single.just(true)
            .observeOn(AndroidSchedulers.from(Looper.getMainLooper()))
            .flatMap {
                val realm = Realm.getDefaultInstance()
                val result = realm.where(FeedItem::class.java).findAllAsync()
                Single.create<DatabaseResult<FeedResponse>> { emitter ->
                    result.addChangeListener { results ->
                        if (results.isNullOrEmpty().not()) {
                            val resultArray: ArrayList<FeedItem> = ArrayList()
                            resultArray.addAll(realm.copyFromRealm(results))
                            val mProfilerResponse = FeedResponse()
                            mProfilerResponse.data = resultArray.toList()
                            emitter.onSuccess(DatabaseSuccess(mProfilerResponse))
                        } else {
                            emitter.onSuccess(
                                DatabaseFailure(
                                    HttpError(
                                        Throwable(
                                            App.getApplicationContext()
                                                .getString(R.string.list_not_found)
                                        ), EC_EMPTY_LIST_IN_DATABASE
                                    )
                                )
                            )
                        }
                    }
                }
            }
    }
}