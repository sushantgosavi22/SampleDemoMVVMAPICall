package com.sushant.sampledemomvvmapicall.repositorys.userrepo

import android.content.Context
import com.sushant.sampledemomvvmapicall.model.ListItemData
import com.sushant.sampledemomvvmapicall.model.ResponseModel
import io.reactivex.Single

interface IUserRepository {
    fun getOrederList(context: Context, page: Int) : Single<ResponseModel>
    fun saveItem(data : ListItemData?) : Single<Boolean>
}