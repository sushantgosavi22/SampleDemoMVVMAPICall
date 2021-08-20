package com.sushant.sampledemomvvmapicall.model

import com.sushant.sampledemomvvmapicall.service.model.BaseResponse

open class FeedResponse : BaseResponse<FeedResponse>() {
    var rows: ArrayList<FeedItem>? = null
    var page: Int? = null
    var title: String ? = null
}
