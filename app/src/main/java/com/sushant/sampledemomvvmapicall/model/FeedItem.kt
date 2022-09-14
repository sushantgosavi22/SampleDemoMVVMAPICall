package com.sushant.sampledemomvvmapicall.model

import java.io.Serializable

data class FeedItem(
    var title: String? = null,
    var image_url: String? = null,
    var content_url: String? = null,
    var date_added: String? = null
) : Serializable