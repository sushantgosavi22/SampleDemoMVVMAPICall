package com.sushant.sampledemomvvmapicall.service.provider

import android.content.Context
import com.sushant.sampledemomvvmapicall.model.ResponseModel
import io.reactivex.Single

interface IServiceProvider {
    fun gerOrderList(context: Context, page: Int): Single<ResponseModel>
}