package com.sushant.sampledemomvvmapicall.database.provider

import android.os.Looper
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.application.App
import com.sushant.sampledemomvvmapicall.database.helper.*
import com.sushant.sampledemomvvmapicall.model.ProfilerItemData
import com.sushant.sampledemomvvmapicall.model.ProfilerResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.realm.Realm

object DatabaseProvider : IDatabaseProvider {

    override fun saveUser(model: ProfilerItemData?): Single<Boolean> {
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

    override fun getUsersFromDatabase(): Single<DatabaseResult<ProfilerResponse>> {
        return Single.just(true)
            .observeOn(AndroidSchedulers.from(Looper.getMainLooper()))
            .flatMap {
                val realm = Realm.getDefaultInstance()
                val result = realm.where(ProfilerItemData::class.java).findAllAsync()
                Single.create<DatabaseResult<ProfilerResponse>> { emitter ->
                    result.addChangeListener { results ->
                        if (results.isNullOrEmpty().not()) {
                            val resultArray: ArrayList<ProfilerItemData> = ArrayList()
                            resultArray.addAll(realm.copyFromRealm(results))
                            val mProfilerResponse = ProfilerResponse()
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