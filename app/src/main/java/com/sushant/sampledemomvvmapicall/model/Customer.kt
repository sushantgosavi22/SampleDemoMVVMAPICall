package com.sushant.sampledemomvvmapicall.model

import java.io.Serializable

data class Customer (
	val firstName : String?=null,
	val lastName : String?=null,
	val address : String?=null,
	val city : String?=null,
	val state : String?=null,
	val zip : String ?=null,
	val zipSuffix : Int?=null,
	val phoneNumber : String?=null
) : Serializable