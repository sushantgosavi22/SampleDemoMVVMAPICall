package com.sushant.sampledemomvvmapicall.model

import java.io.Serializable

open class FeedItem : Serializable {
    val id : Int? =null
    var imageHref: String? = null
    var setup: String? = null
    var type: String? = null
    val punchline : String? = null
}
