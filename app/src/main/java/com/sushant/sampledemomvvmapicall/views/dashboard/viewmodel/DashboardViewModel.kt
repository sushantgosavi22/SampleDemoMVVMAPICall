package com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.sushant.sampledemomvvmapicall.constant.Constant
import com.sushant.sampledemomvvmapicall.model.ConfigError
import com.sushant.sampledemomvvmapicall.model.DataResponse
import com.sushant.sampledemomvvmapicall.model.FeedResponse
import com.sushant.sampledemomvvmapicall.repositorys.feedrepo.IFeedRepository
import com.sushant.sampledemomvvmapicall.views.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class DashboardViewModel(
    application: Application,
    private val mIFeedRepository: IFeedRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(application) {
    private var mApiResponse: MutableLiveData<DataResponse<FeedResponse>> =
        getPersistedFeedResponse()
    val mApiResponseTest: LiveData<DataResponse<FeedResponse>> = mApiResponse

    fun getFeeds(page: Int = Constant.FIRST_PAGE) {
        val disposable = mIFeedRepository.getFeeds(page)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _ ->
                onShowLoading()
                setFeedResponse(DataResponse.loading())
            }.doOnError { error ->
                if (error is ConfigError) {
                    setFeedResponse(DataResponse.configError(error))
                } else {
                    setFeedResponse(DataResponse.error(error))
                }
            }.subscribe({
                setFeedResponse(DataResponse.success(it))
            }, {
                setFeedResponse(DataResponse.error(it))
            })

        compositeDisposable.add(disposable)
    }


    fun onRefresh() {
        resetPageCount()
        onShowLoading()
        getFeeds()
    }

    fun getUserApiResponse(): MutableLiveData<DataResponse<FeedResponse>> = mApiResponse

    private fun setFeedResponse(response: DataResponse<FeedResponse>) =
        savedStateHandle.set(Constant.RESPONSE, response)

    private fun getPersistedFeedResponse(): MutableLiveData<DataResponse<FeedResponse>> =
        savedStateHandle.getLiveData(Constant.RESPONSE)

    fun isPersistedAvailable(): LiveData<Boolean> =
        savedStateHandle.getLiveData<Boolean>(Constant.PERSISTED, false)

    fun setPersisted(boolean: Boolean) = savedStateHandle.set(Constant.PERSISTED, boolean)

    class DashboardViewModelFactory(
        private var app: Application,
        private var repo: IFeedRepository,
        private var mSavedStateHandle: SavedStateHandle
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(
                Application::class.java,
                IFeedRepository::class.java,
                SavedStateHandle::class.java
            ).newInstance(app, repo, mSavedStateHandle)
        }
    }
}