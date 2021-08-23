package com.sushant.sampledemomvvmapicall.service.provider

import com.sushant.sampledemomvvmapicall.service.socket.SocketResponse
import io.reactivex.Observable

interface IServiceProvider {
    fun getFeeds(page: Int): Observable<SocketResponse>
}