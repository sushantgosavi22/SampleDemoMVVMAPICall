package com.sushant.sampledemomvvmapicall.constant

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.TextView
import android.widget.Toast
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    const val KEY_REQUEST_ID: Int = 101
    const val KEY_ITEM: String = "item"
    const val FIRST_PAGE: Int = 1
    const val PAGE_SIZE = 10


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


    fun getLocalFormatterDate(milliSeconds: Long?): String? {
        try {
            milliSeconds?.let {
                val dateFormatter: DateFormat = SimpleDateFormat("MMM dd yyyy")
                return dateFormatter.format(Date(milliSeconds))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }


    public fun getUserTitle(firstName: String?, lastName: String?) =
        firstName?.plus(" ".plus(lastName ?: "")) ?: lastName ?: ""

}