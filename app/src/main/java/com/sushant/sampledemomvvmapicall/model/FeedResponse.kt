package com.sushant.sampledemomvvmapicall.model

import com.sushant.sampledemomvvmapicall.service.model.BaseResponse

open class FeedResponse : BaseResponse<FeedResponse>() {
    var data: List<FeedItem>? = null
    var page: Int? = null
}
