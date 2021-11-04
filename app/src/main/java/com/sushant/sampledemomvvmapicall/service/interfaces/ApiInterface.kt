package com.sushant.sampledemomvvmapicall.service.interfaces
import com.sushant.sampledemomvvmapicall.model.FeedResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("v2/assets")
    fun getFeeds(@Query("limit") limit: Int?=0,@Query("offset") offset: Int? = 0): Single<FeedResponse>

}