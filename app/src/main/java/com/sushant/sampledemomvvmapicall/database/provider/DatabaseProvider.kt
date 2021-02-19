package com.sushant.sampledemomvvmapicall.database.provider

import com.sushant.sampledemomvvmapicall.database.helper.*
import com.sushant.sampledemomvvmapicall.model.ProfilerItemData
import com.sushant.sampledemomvvmapicall.model.ProfilerResponse
import io.realm.Realm

object DatabaseProvider :
    IDatabaseProvider {
    override fun saveUser(mListener: IDatabaseResultListener<Boolean>, model: ProfilerItemData?) {
        model?.let {
            val realm = Realm.getDefaultInstance()
            realm.executeTransactionAsync({ realm ->
                model.id = Math.random().toInt()
                realm.copyToRealm(model)
            }, {
                mListener.onResult(DatabaseSuccess(true))
            }) {
                mListener.onResult(DatabaseFailure(HttpError(it,EC_ENABLE_TO_SAVE_USER)))
            }
        } ?: mListener.onResult(DatabaseFailure(HttpError(Throwable(),EC_NULL_USER_OBJECT_GIVEN)))
    }

    override fun getUserListFromDatabase(mListener: IDatabaseResultListener<ProfilerResponse>) {
        val realm = Realm.getDefaultInstance()
        val result = realm.where(ProfilerItemData::class.java).findAllAsync()
        result.addChangeListener { results ->
            if (results.isNullOrEmpty().not()) {
                val resultArray: ArrayList<ProfilerItemData> = ArrayList()
                resultArray.addAll(realm.copyFromRealm(results))
                var mProfilerResponse = ProfilerResponse()
                mProfilerResponse.data = resultArray.toList()
                mListener.onResult(DatabaseSuccess(mProfilerResponse))
            } else {
                mListener.onResult(DatabaseFailure(HttpError(Throwable("User list is empty."),EC_EMPTY_LIST_IN_DATABASE)))
            }
        }
    }
}