package com.sushant.sampledemomvvmapicall.database.provider

import com.sushant.sampledemomvvmapicall.database.helper.IDatabaseResultListener
import com.sushant.sampledemomvvmapicall.model.ProfilerItemData
import com.sushant.sampledemomvvmapicall.model.ProfilerResponse

interface IDatabaseProvider {
    fun saveUser(mListener : IDatabaseResultListener<Boolean>, profilerItemData: ProfilerItemData?)
    fun getUserListFromDatabase(mListener: IDatabaseResultListener<ProfilerResponse>)
}