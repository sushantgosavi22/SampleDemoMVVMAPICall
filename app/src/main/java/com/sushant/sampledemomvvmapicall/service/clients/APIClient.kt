package com.sushant.sampledemomvvmapicall.service.clients

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.sushant.sampledemomvvmapicall.service.interfaces.ApiInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {

    private const val baseURL: String = "https://reqres.in/api/"
    val client: Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(baseURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
                .addInterceptor(ConnectivityInterceptor())
                .addInterceptor(loggerInterceptor())
                .build()
    }


    private fun loggerInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    val apiServices: ApiInterface by lazy { client.create(ApiInterface::class.java) }
}