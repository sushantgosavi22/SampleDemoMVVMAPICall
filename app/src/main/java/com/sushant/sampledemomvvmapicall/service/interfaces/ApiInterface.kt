package com.sushant.sampledemomvvmapicall.service.interfaces
import com.sushant.sampledemomvvmapicall.model.ProfilerResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("users")
    fun getUsers(@Query("page") page: Int?): Single<ProfilerResponse>

}