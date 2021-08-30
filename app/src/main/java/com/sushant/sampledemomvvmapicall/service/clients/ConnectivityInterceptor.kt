package com.sushant.sampledemomvvmapicall.service.clients

import android.content.Context
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.constant.Utils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor(private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (Utils.isInternetConnected(context).not()) {
            throw IOException(context.getString(R.string.no_internet_connection))
        } else {
            return chain.proceed(chain.request())
        }
    }
}