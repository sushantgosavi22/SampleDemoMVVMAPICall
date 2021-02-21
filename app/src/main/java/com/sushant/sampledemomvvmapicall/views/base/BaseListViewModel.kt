package com.sushant.sampledemomvvmapicall.views.base

import android.app.Application
import androidx.databinding.ObservableBoolean
import com.sushant.sampledemomvvmapicall.constant.Utils


abstract class BaseListViewModel<T>(application: Application) : BaseViewModel(application) {
    protected var page = Utils.FIRST_PAGE
    private val loadingCurrentPage = ObservableBoolean(false)
    val isLoading = ObservableBoolean()
    fun setCurrentPageLoading(loading : Boolean) = loadingCurrentPage.set(loading)
    fun incrementPageCount() = page++
    fun onShowLoading() = isLoading.set(true)
    fun onStopLoading() = isLoading.set(false)

    abstract fun onRefresh()

    protected fun resetPaging() {
        page =  Utils.FIRST_PAGE
    }
}