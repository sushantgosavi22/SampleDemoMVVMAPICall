package com.sushant.sampledemomvvmapicall.service.clients

import com.sushant.sampledemomvvmapicall.application.App
import com.sushant.sampledemomvvmapicall.constant.Utils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (Utils.isInternetConnected(App.getApplicationContext()).not()) {
            throw IOException("No internet connection")
        } else {
            return chain.proceed(chain.request())
        }
    }
}