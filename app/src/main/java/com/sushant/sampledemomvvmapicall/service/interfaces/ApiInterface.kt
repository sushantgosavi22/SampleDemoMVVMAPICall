package com.sushant.sampledemomvvmapicall.service.interfaces
import com.sushant.sampledemomvvmapicall.model.FeedResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("users")
    fun getFeeds(@Query("page") page: Int?): Single<FeedResponse>
}