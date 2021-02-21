package com.sushant.sampledemomvvmapicall.service.provider

import android.content.Context
import com.sushant.sampledemomvvmapicall.model.ResponseModel
import com.sushant.sampledemomvvmapicall.service.clients.APIClient.apiServices
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

object ServiceProvider : IServiceProvider {
    override fun gerOrderList(context: Context, page: Int): Single<ResponseModel> {
        return apiServices.getOrderList(page).subscribeOn(Schedulers.io())
    }
}