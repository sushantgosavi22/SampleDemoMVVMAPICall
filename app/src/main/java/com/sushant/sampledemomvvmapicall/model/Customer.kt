package com.sushant.sampledemomvvmapicall.model

import io.realm.RealmObject
import java.io.Serializable

open class Customer : RealmObject(), Serializable {
	var firstName : String?=null
	var lastName : String?=null
	var address : String?=null
	var city : String?=null
	var state : String?=null
	var zip : String ?=null
	var zipSuffix : Int?=null
	var phoneNumber : String?=null
}