package com.sushant.sampledemomvvmapicall.constant

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast

object Utils {

    const val PERSISTED: String = "persisted"
    const val RESPONSE: String = "response"
    const val FIRST_PAGE: Int = 1

    fun showToast(context: Context?, message: String?) {
        context?.let {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("MissingPermission")
    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
                getNetworkCapabilities(this)
            } ?: false
        } else {
            @Suppress("DEPRECATION")
            connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting ?: false
        }
    }

    /**
     * Get the network capabilities.
     *
     * @param networkCapabilities: Object of [NetworkCapabilities]
     */
    @SuppressLint("NewApi")
    private fun getNetworkCapabilities(networkCapabilities: NetworkCapabilities): Boolean =
        networkCapabilities.run {
            when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }

}