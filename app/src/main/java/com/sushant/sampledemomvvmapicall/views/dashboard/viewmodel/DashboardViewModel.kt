package com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.model.Circles
import com.sushant.sampledemomvvmapicall.model.UserResponse
import com.sushant.sampledemomvvmapicall.model.Users
import com.sushant.sampledemomvvmapicall.repositorys.feedrepo.IFeedRepository
import com.sushant.sampledemomvvmapicall.repositorys.feedrepo.FeedRepository
import com.sushant.sampledemomvvmapicall.service.model.ApiResponse
import com.sushant.sampledemomvvmapicall.views.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import java.util.ArrayList

class DashboardViewModel(application: Application) : BaseViewModel(application) {
    var mIFeedRepository: IFeedRepository = FeedRepository()
    var mApiResponse = MutableLiveData<ApiResponse<UserResponse>>()

    fun getUsers(page : Int = Utils.FIRST_PAGE) {
        mIFeedRepository.getFeeds(page)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { disposable ->
                onShowLoading()
                mApiResponse.value = ApiResponse.loading()
            }
            .subscribe(object : DisposableSingleObserver<UserResponse>() {
                override fun onSuccess(t: UserResponse) {
                    if (page == Utils.FIRST_PAGE) {
                        mApiResponse.value = ApiResponse.clearListAndHideError()
                    }
                    mApiResponse.value = ApiResponse.success(t)
                }

                override fun onError(e: Throwable) {
                    if (page == Utils.FIRST_PAGE) {
                        mApiResponse.value = ApiResponse.emptyList()
                    }
                    mApiResponse.value = ApiResponse.error(e)
                }
            })
    }


    fun onRefresh() {
        resetPageCount()
        onShowLoading()
        getUsers()
    }

    fun getUserApiResponse(): MutableLiveData<ApiResponse<UserResponse>> {
        return mApiResponse
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun getUsersFromResponse(apiResponse: UserResponse): ArrayList<Users> {
        val users =ArrayList<Users>()
        apiResponse.circles?.forEach {
            users.addAll(it.users)
        }
        return users
    }

    fun getCirclesFromResponse(apiResponse: UserResponse,usersData: Users): ArrayList<Circles> {
        val circlesList =ArrayList<Circles>()
        apiResponse.circles?.forEach {circles->
            for (index in circles.users.indices) {
                if(usersData.id== circles.users[index].id){
                    circlesList.add(circles)
                    break
                }
            }
        }
        return circlesList
    }

}