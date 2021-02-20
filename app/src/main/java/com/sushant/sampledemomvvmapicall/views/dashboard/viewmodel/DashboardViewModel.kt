package com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.sushant.sampledemomvvmapicall.model.ProfilerResponse
import com.sushant.sampledemomvvmapicall.repositorys.userrepo.IUserRepository
import com.sushant.sampledemomvvmapicall.repositorys.userrepo.UserRepository
import com.sushant.sampledemomvvmapicall.service.model.ApiResponse
import com.sushant.sampledemomvvmapicall.views.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

class DashboardViewModel(application: Application) : BaseViewModel(application) {
    var mIUserRepository: IUserRepository = UserRepository()
    var mApiResponse = MutableLiveData<ApiResponse<ProfilerResponse>>()

    fun getUsers(page: Int) {
        mApiResponse.value = ApiResponse.loading()
        mIUserRepository.getUsers(getApplication(), page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<ProfilerResponse>() {
                override fun onSuccess(t: ProfilerResponse) {
                    mApiResponse.value = ApiResponse.success(t)
                }

                override fun onError(e: Throwable) {
                    mApiResponse.value = ApiResponse.error(e)
                }
            })
    }

    fun getUserApiResponse(): MutableLiveData<ApiResponse<ProfilerResponse>> {
        return mApiResponse
    }

    override fun onCleared() {
        super.onCleared()
    }

}