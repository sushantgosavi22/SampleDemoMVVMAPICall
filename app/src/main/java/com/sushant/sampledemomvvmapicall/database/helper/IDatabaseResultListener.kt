package com.sushant.sampledemomvvmapicall.database.helper

import com.sushant.sampledemomvvmapicall.database.helper.DatabaseResult
import com.sushant.sampledemomvvmapicall.model.ProfilerResponse

interface IDatabaseResultListener<T : Any>{
    fun onResult(databaseResult: DatabaseResult<T>)
}