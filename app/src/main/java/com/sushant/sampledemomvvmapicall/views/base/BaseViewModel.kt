package com.sushant.sampledemomvvmapicall.views.base

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sushant.sampledemomvvmapicall.constant.Utils


open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    protected var page = Utils.FIRST_PAGE
    /**
     * Observer SwipeToRefresh
     */
    val isLoading = ObservableBoolean()

    /**
     * Show SwipeToRefresh.
     */
    fun onShowLoading() = isLoading.set(true)

    /**
     * Hide SwipeToRefresh.
     */
    fun onStopLoading() = isLoading.set(false)

    /**
     * increment Page Count value.
     */
    fun incrementPageCount() = page++

    fun resetPageCount()  {
        page =Utils.FIRST_PAGE
    }
    override fun onCleared() {
        super.onCleared()
    }
}