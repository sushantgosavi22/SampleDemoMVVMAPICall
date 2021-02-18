package com.sushant.sampledemomvvmapicall.service.interfaces
import com.sushant.sampledemomvvmapicall.model.OrderResponse
import retrofit2.Call
import retrofit2.http.GET


interface ApiInterface {
    @GET("5c8b7873360000881d8f80ed")
    fun getOrderList(): Call<OrderResponse>
}