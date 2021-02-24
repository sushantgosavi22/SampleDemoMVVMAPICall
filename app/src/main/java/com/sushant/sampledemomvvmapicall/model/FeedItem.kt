package com.sushant.sampledemomvvmapicall.model

import androidx.recyclerview.widget.DiffUtil
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class FeedItem : RealmObject(), Serializable {
    @PrimaryKey
    var id: Int? = null
    var avatar: String? = null
    var email: String? = null
    var first_name: String? = null
    var last_name: String? = null
    var isActive: Boolean = false

    companion object {
        public var callBack = object : DiffUtil.ItemCallback<FeedItem>() {
            override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean=
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean =
                oldItem.equals(newItem)
        }
    }
}
