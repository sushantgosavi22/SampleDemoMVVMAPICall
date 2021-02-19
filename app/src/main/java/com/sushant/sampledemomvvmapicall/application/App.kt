package com.sushant.sampledemomvvmapicall.application

import android.app.Application
import com.sushant.sampledemomvvmapicall.R
import io.realm.Realm
import io.realm.RealmConfiguration

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val appName =getString(R.string.app_name)
        val config = RealmConfiguration.Builder()
            .name(appName.plus(".realm"))
            .build()
        Realm.setDefaultConfiguration(config)
    }

}