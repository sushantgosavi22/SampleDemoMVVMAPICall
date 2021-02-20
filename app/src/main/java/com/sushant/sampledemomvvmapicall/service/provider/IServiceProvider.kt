package com.sushant.sampledemomvvmapicall.service.provider

import android.content.Context
import com.sushant.sampledemomvvmapicall.model.ProfilerResponse
import io.reactivex.Single

interface IServiceProvider {
    fun getUsers(context: Context, page: Int): Single<ProfilerResponse>
}