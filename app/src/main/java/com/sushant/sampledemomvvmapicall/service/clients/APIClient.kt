package com.sushant.sampledemomvvmapicall.service.clients

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {

    companion object {
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
                .build()
        }

    }
}