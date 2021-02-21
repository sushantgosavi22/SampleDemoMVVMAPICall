package com.sushant.sampledemomvvmapicall.views.details.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.sushant.sampledemomvvmapicall.model.ListItemData
import com.sushant.sampledemomvvmapicall.repositorys.userrepo.IUserRepository
import com.sushant.sampledemomvvmapicall.repositorys.userrepo.UserRepository
import com.sushant.sampledemomvvmapicall.views.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

class DetailsViewModel(application: Application) : BaseViewModel(application) {
    var mIUserRepository: IUserRepository = UserRepository()
    var mSaveUserCallBack = MutableLiveData<Throwable>()

    fun saveItem(data: ListItemData?) {
        mIUserRepository.saveItem(data)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<Boolean>() {
                override fun onSuccess(t: Boolean) {
                    mSaveUserCallBack.value = null
                }

                override fun onError(e: Throwable) {
                    mSaveUserCallBack.value = e
                }
            })
    }

    fun getSaveCallBack(): MutableLiveData<Throwable> {
        return mSaveUserCallBack
    }

    fun shouldEnable(item: Int?) =(item!=null)
    fun shouldAddButtonVisible(item: Int?) =(item!=null)
}