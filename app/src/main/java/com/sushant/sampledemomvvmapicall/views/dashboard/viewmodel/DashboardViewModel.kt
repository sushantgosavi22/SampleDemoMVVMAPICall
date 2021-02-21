package com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.model.ProfilerItemData
import com.sushant.sampledemomvvmapicall.model.ProfilerResponse
import com.sushant.sampledemomvvmapicall.repositorys.userrepo.IUserRepository
import com.sushant.sampledemomvvmapicall.repositorys.userrepo.UserRepository
import com.sushant.sampledemomvvmapicall.service.model.ApiResponse
import com.sushant.sampledemomvvmapicall.views.adapter.pagination.*
import com.sushant.sampledemomvvmapicall.views.base.BaseListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

class DashboardViewModel(application: Application) :
    BaseListViewModel<ProfilerItemData>(application) {
    var mIUserRepository: IUserRepository = UserRepository()
    var mApiResponse = MutableLiveData<ApiResponse<ProfilerResponse>>()
    private val itemDataSourceFactory by lazy {
        ItemDataSourceFactory()
    }

    fun getUsers(forcedRefresh: Boolean = false,isPaginationOn: Boolean = false, page: Int, callBack: PageLoadingCallBack? = null) {
        setCurrentPageLoading(true)
        val pageToRequest = if (forcedRefresh) Utils.FIRST_PAGE else page
        mIUserRepository.getUsers(getApplication(), pageToRequest)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { disposable -> mApiResponse.value = ApiResponse.loading() }
            .doFinally { setCurrentPageLoading(false) }
            .subscribe(object : DisposableSingleObserver<ProfilerResponse>() {
                override fun onSuccess(t: ProfilerResponse) {
                    if (forcedRefresh) resetPaging()
                    if (pageToRequest == Utils.FIRST_PAGE) {
                        mApiResponse.value = ApiResponse.clearListAndHideError()
                    }
                    handleRefreshCallBack(callBack, t,pageToRequest,isPaginationOn)
                    mApiResponse.value = ApiResponse.success(t)
                    incrementPageCount()
                }

                override fun onError(e: Throwable) {
                    if (pageToRequest == Utils.FIRST_PAGE) {
                        mApiResponse.value = ApiResponse.emptyList()
                    }
                    mApiResponse.value = ApiResponse.error(e)
                }
            })
    }

    fun handleRefreshCallBack(callBack: PageLoadingCallBack?, response: ProfilerResponse,pageToRequest : Int,isPaginationOn: Boolean) {
        try {
            callBack?.let {
                if (isPaginationOn.not()) {
                    when (it) {
                        is LoadInitial -> {
                            response.data?.let { list ->
                                if (pageToRequest == Utils.FIRST_PAGE) {
                                    it.callback.onResult(list, null, callBack.pageValue)
                                } else {
                                    it.callback.onResult(list, null, callBack.pageValue + 1)
                                }
                            }
                        }
                    }
                    return
                }
                response.data?.let { list ->
                    when (it) {
                        is LoadInitial -> {
                            if (pageToRequest == Utils.FIRST_PAGE ) {
                                it.callback.onResult(list, null, callBack.pageValue)
                            }else{
                                it.callback.onResult(list, null, callBack.pageValue + 1)
                            }
                        }
                        is LoadBefore -> it.callback.onResult(list, if (callBack.pageValue > 1) callBack.pageValue - 1 else 1)
                        is LoadAfter -> it.callback.onResult(list, callBack.pageValue + 1)
                    }
                }

            }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    override fun onRefresh() {
        onShowLoading()

    }

    var itemPagedList : LiveData<PagedList<ProfilerItemData>>? = null
    fun callPaginatedApi() {
        val config: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Utils.PAGE_SIZE)
            .build()
        itemPagedList = LivePagedListBuilder(itemDataSourceFactory, config).build()
    }

    fun getPagedList():LiveData<PagedList<ProfilerItemData>>? {
        return itemPagedList
    }

    fun getUserApiResponse(): MutableLiveData<ApiResponse<ProfilerResponse>> {
        return mApiResponse
    }

    fun getLoadCallback(): MutableLiveData<PageLoadingCallBack> {
        return itemDataSourceFactory.getLoadCallback()
    }

    override fun onCleared() {
        super.onCleared()
    }

}