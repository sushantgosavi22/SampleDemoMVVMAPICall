package com.sushant.sampledemomvvmapicall.di

import com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel.DashboardViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(dashboardViewModelFactory: DashboardViewModelFactory)
}