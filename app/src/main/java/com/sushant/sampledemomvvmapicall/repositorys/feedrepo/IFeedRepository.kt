package com.sushant.sampledemomvvmapicall.repositorys.feedrepo

import android.content.Context
import com.sushant.sampledemomvvmapicall.model.ProfilerResponse
import io.reactivex.Single

interface IFeedRepository {
    fun getFeeds(page: Int) : Single<ProfilerResponse>
}