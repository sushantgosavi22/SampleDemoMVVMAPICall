package com.sushant.sampledemomvvmapicall.model

import androidx.recyclerview.widget.DiffUtil
import io.realm.RealmObject
import java.io.Serializable

open class ProfilerItemData : RealmObject(), Serializable {
    var avatar: String? = null
    var email: String? = null
    var first_name: String? = null
    var id: Int? = null
    var last_name: String? = null

    companion object {
        public var callBack = object : DiffUtil.ItemCallback<ProfilerItemData>() {
            override fun areItemsTheSame(oldItem: ProfilerItemData, newItem: ProfilerItemData): Boolean=
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: ProfilerItemData, newItem: ProfilerItemData): Boolean =
                oldItem.equals(newItem)
        }
    }
}
