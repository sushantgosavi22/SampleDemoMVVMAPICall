package com.sushant.sampledemomvvmapicall.service.provider

import com.sushant.sampledemomvvmapicall.service.socket.SocketHelper
import com.sushant.sampledemomvvmapicall.service.socket.SocketResponse
import io.reactivex.Observable


object ServiceProvider : IServiceProvider {


    override fun getFeeds(page: Int): Observable<SocketResponse> {
        return SocketHelper.getFeed()
    }
}