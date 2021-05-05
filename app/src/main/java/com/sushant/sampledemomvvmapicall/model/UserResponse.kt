package com.sushant.sampledemomvvmapicall.model

import com.sushant.sampledemomvvmapicall.service.model.BaseResponse


open class UserResponse : BaseResponse<UserResponse>() {
	val loggedInUser : LoggedInUser?  =null
	val circles : List<Circles>? = ArrayList()
}