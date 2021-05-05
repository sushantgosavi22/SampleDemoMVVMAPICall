package com.sushant.sampledemomvvmapicall.service.provider

import com.google.gson.Gson
import com.sushant.sampledemomvvmapicall.application.App
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.model.UserResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

object ServiceProvider : IServiceProvider {
    override fun getFeeds(page: Int): Single<UserResponse> {
        return getUserResponse().subscribeOn(Schedulers.io())
    }

    private fun getUserResponse(): Single<UserResponse> {
        val response: UserResponse =Gson().fromJson(Utils.loadJSONFromAsset(App.getApplicationContext()), UserResponse::class.java)
        return Single.just(response);
    }

}