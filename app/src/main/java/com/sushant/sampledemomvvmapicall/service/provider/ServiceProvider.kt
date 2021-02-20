package com.sushant.sampledemomvvmapicall.service.provider

import android.content.Context
import com.sushant.sampledemomvvmapicall.model.ProfilerResponse
import com.sushant.sampledemomvvmapicall.service.clients.APIClient
import com.sushant.sampledemomvvmapicall.service.interfaces.ApiInterface
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

object ServiceProvider : IServiceProvider{
    override fun getUsers(context: Context, page: Int): Single<ProfilerResponse> {
        val apiServices = APIClient.client.create(ApiInterface::class.java)
        return  apiServices.getUsers(page).subscribeOn(Schedulers.io())
    }
}