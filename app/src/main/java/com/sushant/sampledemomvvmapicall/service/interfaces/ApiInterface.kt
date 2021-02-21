package com.sushant.sampledemomvvmapicall.service.interfaces
import com.sushant.sampledemomvvmapicall.model.ResponseModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("5c8b7873360000881d8f80ed")
    fun getOrderList(@Query("page") page: Int?): Single<ResponseModel>

}