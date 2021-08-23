package com.sushant.sampledemomvvmapicall.repositorys.feedrepo

import com.sushant.sampledemomvvmapicall.service.socket.SocketResponse
import io.reactivex.Observable

interface IFeedRepository {
    fun getFeeds(page: Int): Observable<SocketResponse>
}