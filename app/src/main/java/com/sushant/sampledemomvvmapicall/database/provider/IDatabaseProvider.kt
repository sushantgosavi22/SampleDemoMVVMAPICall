package com.sushant.sampledemomvvmapicall.database.provider

import com.sushant.sampledemomvvmapicall.database.helper.DatabaseResult
import com.sushant.sampledemomvvmapicall.model.ListItemData
import com.sushant.sampledemomvvmapicall.model.ResponseModel
import io.reactivex.Single

interface IDatabaseProvider {
    fun saveItem(model: ListItemData?) : Single<Boolean>
    fun getItemsFromDatabase() : Single<DatabaseResult<ResponseModel>>
}