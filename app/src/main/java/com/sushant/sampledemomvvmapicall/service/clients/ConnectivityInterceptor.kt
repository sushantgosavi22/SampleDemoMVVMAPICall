package com.sushant.sampledemomvvmapicall.service.clients

import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.application.App
import com.sushant.sampledemomvvmapicall.constant.Utils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (Utils.isInternetConnected(App.getApplicationContext()).not()) {
            throw IOException(App.getApplicationContext().getString(R.string.no_internet_connection))
        } else {
            return chain.proceed(chain.request())
        }
    }
}