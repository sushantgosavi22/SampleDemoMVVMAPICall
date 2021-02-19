package com.sushant.sampledemomvvmapicall.constant

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    const val KEY_REQUEST_ID : Int = 101
    const val KEY_ITEM : String ="item"


    fun showToast(context: Context?, message: String?) {
        context?.let {
            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("MissingPermission")
    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
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
    private fun getNetworkCapabilities(networkCapabilities: NetworkCapabilities): Boolean = networkCapabilities.run {
        when {
            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


    @SuppressLint("SimpleDateFormat")
    fun getScheduleTime(time: String?): Long {
        try{
            time?.let {
                val dateFormatter: DateFormat =SimpleDateFormat("yyyy-MM-dd");
                val date: Date = dateFormatter.parse(time)
                return date.time
            }
        }catch (e :Exception){
            e.printStackTrace()
        }
        return System.currentTimeMillis()
    }

    fun getLocalFormatterDate(milliSeconds: Long?): String? {
        try{
            milliSeconds?.let {
                val dateFormatter: DateFormat = SimpleDateFormat("MMM dd yyyy")
                return dateFormatter.format(Date(milliSeconds))
            }
        }catch (e :Exception){
            e.printStackTrace()
        }
        return ""
    }

    @SuppressLint("SimpleDateFormat")
    fun getLocalFormattedTime(time: String?): String{
        var result = ""
        time?.let {
            val inputFormat = SimpleDateFormat("KK:mm:ss")
            val outputFormat = SimpleDateFormat("hh:mm a")
            try {
                result =  outputFormat.format(inputFormat.parse(time))
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        return result
    }


}