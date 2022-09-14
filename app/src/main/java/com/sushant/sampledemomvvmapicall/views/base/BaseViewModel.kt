package com.sushant.sampledemomvvmapicall.views.base

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import com.sushant.sampledemomvvmapicall.constant.Constant
import io.reactivex.disposables.CompositeDisposable


open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private var page = Constant.FIRST_PAGE

    val compositeDisposable = CompositeDisposable()

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

    fun resetPageCount() {
        page = Constant.FIRST_PAGE
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}