package com.sushant.sampledemomvvmapicall.service.provider

import com.sushant.sampledemomvvmapicall.model.ProfilerResponse
import com.sushant.sampledemomvvmapicall.service.clients.APIClient.apiServices
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

object ServiceProvider : IServiceProvider {
    override fun getFeeds(page: Int): Single<ProfilerResponse> {
        return apiServices.getFeeds(page).subscribeOn(Schedulers.io())
    }
}