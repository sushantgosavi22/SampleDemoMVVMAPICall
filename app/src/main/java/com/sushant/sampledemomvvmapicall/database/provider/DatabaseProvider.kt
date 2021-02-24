package com.sushant.sampledemomvvmapicall.database.provider

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.application.App
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.database.helper.*
import com.sushant.sampledemomvvmapicall.model.FeedItem
import com.sushant.sampledemomvvmapicall.model.FeedResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.realm.Realm
import io.realm.RealmQuery


object DatabaseProvider : IDatabaseProvider {

    override fun saveFeed(model: FeedItem?): Single<Boolean> {
        if (model == null) return Single.create<Boolean> { it.onError(Throwable()) }
        return Single.just(model)
            .observeOn(AndroidSchedulers.from(Looper.getMainLooper()))
            .flatMap {
                val realm = Realm.getDefaultInstance()
                Single.create<Boolean> { emitter ->
                    realm.executeTransactionAsync({ realm ->
                        it.id = getNextKey(realm)
                        realm.copyToRealm(model)
                    }, {
                        emitter.onSuccess(true)
                    }) {
                        emitter.onError(it)
                    }
                }
            }
    }

    private fun getNextKey(realm: Realm): Int {
        return try {
            val number: Number? = realm.where(FeedItem::class.java).max("id")
            if (number != null) number.toInt() + 1 else 1
        } catch (e: ArrayIndexOutOfBoundsException) {
            1
        }
    }

    override fun getFeedsFromDatabase(page: Int): Single<DatabaseResult<FeedResponse>> {
        return Single.just(true)
            .observeOn(AndroidSchedulers.from(Looper.getMainLooper()))
            .flatMap {
                val fromPage =getFromPage(page)
                val toPage =getToPage(page)
                val realm = Realm.getDefaultInstance()
                var query: RealmQuery<FeedItem> = realm.where(FeedItem::class.java)
                query.between("id", fromPage, toPage)
                val result = query.findAllAsync()
                //val result = realm.where(FeedItem::class.java).findAllAsync()
                Single.create<DatabaseResult<FeedResponse>> { emitter ->
                    result.addChangeListener { results ->
                        if (results.isNullOrEmpty().not()) {
                            val resultArray: ArrayList<FeedItem> = ArrayList()
                            resultArray.addAll(realm.copyFromRealm(results))
                            val mProfilerResponse = FeedResponse()
                            mProfilerResponse.data = resultArray.toList()
                            emitter.onSuccess(DatabaseSuccess(mProfilerResponse))
                        } else {
                            if(page==Utils.FIRST_PAGE){
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
                            }else{
                                val mProfilerResponse = FeedResponse().apply { data=ArrayList() }
                                emitter.onSuccess(DatabaseSuccess(mProfilerResponse))
                            }
                        }
                    }
                }
            }
    }

    private fun getFromPage(page: Int): Int =
        if (page == Utils.FIRST_PAGE) page else ((page * Utils.PAGE_SIZE) - (Utils.PAGE_SIZE - 1))

    private fun getToPage(page: Int): Int =(page * Utils.PAGE_SIZE)

}