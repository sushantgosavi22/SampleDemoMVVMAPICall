package com.sushant.sampledemomvvmapicall.di

import com.sushant.sampledemomvvmapicall.application.App
import dagger.Module
import dagger.Provides

@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
class AppModule constructor(app: App) {

    var application: App = app

    @Provides
    fun provideApplication(): App {
        return application
    }
}