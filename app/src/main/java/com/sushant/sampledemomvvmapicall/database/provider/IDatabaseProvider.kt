package com.sushant.sampledemomvvmapicall.database.provider

import com.sushant.sampledemomvvmapicall.database.helper.DatabaseResult
import com.sushant.sampledemomvvmapicall.model.ProfilerItemData
import com.sushant.sampledemomvvmapicall.model.ProfilerResponse
import io.reactivex.Single

interface IDatabaseProvider {
    fun saveUser(model: ProfilerItemData?) : Single<Boolean>
    fun getUsersFromDatabase() : Single<DatabaseResult<ProfilerResponse>>
}