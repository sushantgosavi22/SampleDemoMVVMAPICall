package com.sushant.sampledemomvvmapicall.model

import io.realm.RealmObject
import java.io.Serializable

open class ProfilerItemData : RealmObject(),Serializable {
    var avatar: String? = null
    var email: String? = null
    var first_name: String? = null
    var id: Int? = null
    var last_name: String? = null
}
