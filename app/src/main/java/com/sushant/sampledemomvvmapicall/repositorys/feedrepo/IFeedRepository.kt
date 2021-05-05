package com.sushant.sampledemomvvmapicall.repositorys.feedrepo

import android.content.Context
import com.sushant.sampledemomvvmapicall.model.UserResponse
import io.reactivex.Single

interface IFeedRepository {
    fun getFeeds(page: Int) : Single<UserResponse>
}