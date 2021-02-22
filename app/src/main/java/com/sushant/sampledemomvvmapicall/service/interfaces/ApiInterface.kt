package com.sushant.sampledemomvvmapicall.service.interfaces
import com.sushant.sampledemomvvmapicall.model.ProfilerResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("s/2iodh4vg0eortkl/facts.json")
    fun getFeeds(@Query("page") page: Int?): Single<ProfilerResponse>

}