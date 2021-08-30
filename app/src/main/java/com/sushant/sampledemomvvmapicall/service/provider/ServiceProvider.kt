package com.sushant.sampledemomvvmapicall.service.provider

import com.sushant.sampledemomvvmapicall.model.FeedResponse
import com.sushant.sampledemomvvmapicall.service.interfaces.ApiInterface
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ServiceProvider(private val apiInterface: ApiInterface) : IServiceProvider {
    override fun getFeeds(): Single<FeedResponse> {
        return apiInterface.getFeeds().subscribeOn(Schedulers.io()).flatMap {response->
            if(response.isNotEmpty()){
                Single.just(FeedResponse().apply { rows = ArrayList(response) })
            }else{
                Single.error(Throwable("List is empty!"))
            }
        }
    }
}