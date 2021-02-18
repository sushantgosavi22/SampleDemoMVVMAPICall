package com.sushant.sampledemomvvmapicall.model

import java.io.Serializable

data class Customers  (
    val orderRef : String?=null,
    val status : String?=null,
    var isActive : Boolean= false,
    val scheduleDate : String?=null,
    val lastUpdateDate : String?=null,
    val scheduleStartTime : String?=null,
    val scheduleEndTime : String?=null,
    val customer : Customer?=null,
    val serviceRequested : String?=null,
    val serviceSpecialInstructions : String?=null,
    val jobIndicator : String?=null,
    val imageUrl : String?=null
): Serializable