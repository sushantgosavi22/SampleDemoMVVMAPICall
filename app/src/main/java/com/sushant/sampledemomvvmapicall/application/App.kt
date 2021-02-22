package com.sushant.sampledemomvvmapicall.application

import android.app.Application
import android.content.Context

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        context =this
    }

    companion object{
        private lateinit var context: Context
        fun getApplicationContext(): Context {
            return context
        }
    }
}