package com.sushant.sampledemomvvmapicall.model

import androidx.recyclerview.widget.DiffUtil
import io.realm.RealmObject
import java.io.Serializable

open class ListItemData : RealmObject(), Serializable {
    var imageUrl: String? = null
    var id: Int? = null
    var isActive: Boolean = false
    var orderRef : String?=null
    var status : String?=null
    var scheduleDate : String?=null
    var lastUpdateDate : String?=null
    var scheduleStartTime : String?=null
    var scheduleEndTime : String?=null
    var customer : Customer?=null
    var serviceRequested : String?=null
    var serviceSpecialInstructions : String?=null
    var jobIndicator : String?=null


    companion object {
        public var callBack = object : DiffUtil.ItemCallback<ListItemData>() {
            override fun areItemsTheSame(oldItem: ListItemData, newItem: ListItemData): Boolean=
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: ListItemData, newItem: ListItemData): Boolean =
                oldItem.equals(newItem)
        }
    }
}
