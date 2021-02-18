package com.sushant.sampledemomvvmapicall.service.clients

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {

    companion object {
        private const val baseURL: String = "http://www.mocky.io/v2/"
        val client: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}