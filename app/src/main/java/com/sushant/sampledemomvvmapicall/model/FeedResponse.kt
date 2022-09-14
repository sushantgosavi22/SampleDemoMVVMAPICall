package com.sushant.sampledemomvvmapicall.model


data class FeedResponse(var pets: ArrayList<FeedItem>? = null) : BaseResponse<FeedResponse>()
