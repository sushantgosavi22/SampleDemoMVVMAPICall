package com.sushant.sampledemomvvmapicall.model
data class OrderResponse (
	val firstName : String?=null,
	val lastName : String?=null,
	val phoneNumber : String?=null,
	val customers : List<Customers>?=null
)