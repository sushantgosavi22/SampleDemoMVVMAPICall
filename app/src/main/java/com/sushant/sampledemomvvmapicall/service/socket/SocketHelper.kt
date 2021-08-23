package com.sushant.sampledemomvvmapicall.service.socket

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.application.App
import com.sushant.sampledemomvvmapicall.model.FeedItem
import com.sushant.sampledemomvvmapicall.model.FeedResponse
import io.reactivex.Observable
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI


sealed class SocketResponse {
    object SocketInit : SocketResponse()
    data class SocketOpen(val serverHandshake: ServerHandshake?) : SocketResponse()
    data class SocketClose(val i: Int, val s: String, val b: Boolean) : SocketResponse()
    data class SocketMessage(val feedResponse: FeedResponse?) : SocketResponse()
    data class SocketError(val exception: Exception?) : SocketResponse()
}

object SocketHelper {

    const val BASE_URL: String = "ws://city-ws.herokuapp.com/"

    fun getFeed(): Observable<SocketResponse> {
        return Observable.create<SocketResponse> {
            it.onNext(SocketResponse.SocketInit)
            val mWebSocketClient = object : WebSocketClient(URI(BASE_URL)) {
                override fun onOpen(serverHandshake: ServerHandshake?) {
                    it.onNext(SocketResponse.SocketOpen(serverHandshake))
                }

                override fun onMessage(jsonArray: String) {
                    val listType = object : TypeToken<List<FeedItem?>?>() {}.type
                    val items: List<FeedItem> = Gson().fromJson<List<FeedItem>>(jsonArray, listType)
                    if(!items.isNullOrEmpty()){
                        val response = FeedResponse()
                        response.rows = ArrayList(items)
                        response.page = items.size
                        response.title = App.getApplicationContext().getString(R.string.app_name)
                        it.onNext(SocketResponse.SocketMessage(response))
                    }
                }

                override fun onClose(i: Int, s: String, b: Boolean) {
                    it.onNext(SocketResponse.SocketClose(i, s, b))
                }

                override fun onError(e: Exception) {
                    it.onNext(SocketResponse.SocketError(e))
                }
            }
            mWebSocketClient.connect()
        }
    }
}
