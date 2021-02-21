package com.sushant.sampledemomvvmapicall.repositorys.userrepo

import android.content.Context
import com.sushant.sampledemomvvmapicall.model.ProfilerItemData
import com.sushant.sampledemomvvmapicall.model.ProfilerResponse
import io.reactivex.Single

interface IUserRepository {
    fun getUsers(context: Context, page: Int,isPaginationOn: Boolean = false) : Single<ProfilerResponse>
    fun saveUser(data : ProfilerItemData?) : Single<Boolean>
}