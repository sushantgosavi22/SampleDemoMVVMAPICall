package com.sushant.sampledemomvvmapicall.service.provider

import android.content.Context
import com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel.IListCallBack

interface IServiceProvider {
    fun getUsers(context: Context, page: Int, mIListCallBack: IListCallBack)
}