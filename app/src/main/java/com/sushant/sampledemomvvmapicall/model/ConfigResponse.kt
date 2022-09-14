package com.sushant.sampledemomvvmapicall.model

import java.io.Serializable

data class ConfigResponse(val settings : Settings?) : Serializable
data class Settings(val workHours : String?) : Serializable