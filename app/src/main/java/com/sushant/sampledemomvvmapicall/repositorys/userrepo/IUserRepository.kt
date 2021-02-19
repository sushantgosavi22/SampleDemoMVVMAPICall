package com.sushant.sampledemomvvmapicall.repositorys.userrepo

import android.content.Context
import com.sushant.sampledemomvvmapicall.model.ProfilerItemData
import com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel.IListCallBack
import com.sushant.sampledemomvvmapicall.views.details.viewmodel.ISaveUserCallback

interface IUserRepository {
    fun getUsers(context: Context, page: Int, mIListCallBack: IListCallBack)
    fun saveUser(data : ProfilerItemData?, mISaveUserCallback: ISaveUserCallback)
}