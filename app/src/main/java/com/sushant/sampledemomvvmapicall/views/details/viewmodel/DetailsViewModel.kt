package com.sushant.sampledemomvvmapicall.views.details.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.sushant.sampledemomvvmapicall.model.FeedItem
import com.sushant.sampledemomvvmapicall.repositorys.feedrepo.IFeedRepository
import com.sushant.sampledemomvvmapicall.repositorys.feedrepo.FeedRepository
import com.sushant.sampledemomvvmapicall.views.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

class DetailsViewModel(application: Application) : BaseViewModel(application) {
    var mIFeedRepository: IFeedRepository = FeedRepository()
    var mSaveUserCallBack = MutableLiveData<Resource<Boolean>>()

    fun saveFeed(data: FeedItem?) {
        mIFeedRepository.saveFeed(data)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<Boolean>() {
                override fun onSuccess(t: Boolean) {
                    mSaveUserCallBack.value = Resource.success(t)
                }
                override fun onError(e: Throwable) {
                    mSaveUserCallBack.value = Resource.error(e)
                }
            })
    }

    fun getSaveFeedCallBack(): MutableLiveData<Resource<Boolean>> {
        return mSaveUserCallBack;
    }

    fun shouldEnable(item: Int?) =(item==null)
    fun shouldAddButtonVisible(item: Int?) =(item==null)

    data class Resource<out T>(val status: Status, val data: T?, val exception: Throwable?) {
        enum class Status {
            SUCCESS,
            ERROR,
        }
        companion object {
            fun <T> success(data: T?): Resource<T> {
                return Resource(Status.SUCCESS, data, null)
            }

            fun <T> error(exception: Throwable): Resource<T> {
                return Resource(Status.ERROR, null, exception)
            }
        }
    }
}