package com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sushant.sampledemomvvmapicall.application.App
import com.sushant.sampledemomvvmapicall.repositorys.feedrepo.IFeedRepository
import javax.inject.Inject

class DashboardViewModelFactory(
    private var app: Application,
    private var mSavedStateHandle: SavedStateHandle
) : ViewModelProvider.Factory {

    @Inject
    lateinit var repo: IFeedRepository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        App.getAppComponent().inject(this)
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return modelClass.getConstructor(Application::class.java,
                IFeedRepository::class.java,
                SavedStateHandle::class.java).newInstance(app, repo, mSavedStateHandle)
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}