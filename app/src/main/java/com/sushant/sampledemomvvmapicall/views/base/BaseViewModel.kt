package com.sushant.sampledemomvvmapicall.views.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    override fun onCleared() {
        super.onCleared()
    }
}