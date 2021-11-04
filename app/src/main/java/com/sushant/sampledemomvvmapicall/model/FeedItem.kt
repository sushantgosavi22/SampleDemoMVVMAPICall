package com.sushant.sampledemomvvmapicall.model

import java.io.Serializable

open class FeedItem : Serializable {
    var id: String? = null
    var rank: String? = null
    var name: String? = null
    var supply: String? = null
    var maxSupply: String? = null
    var marketCapUsd: String? = null
    var volumeUsd24Hr: String? = null
    var priceUsd: String? = null
    var changePercent24Hr: String? = null
    var vwap24Hr: String? = null
    var imageHref: String? = null
    var description: String? = null
    var title: String? = null
    var isPriceUp : Boolean? = true

    override fun equals(other: Any?): Boolean {
        return other is FeedItem && other.id == this.id && other.rank == this.rank
    }
}
