package com.sushant.sampledemomvvmapicall.model

import java.io.Serializable

open class FeedItem : Serializable {
    var imageHref: String? = null
    var description: String? = null
    var title: String? = null
}
