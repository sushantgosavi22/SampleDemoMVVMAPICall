package com.sushant.sampledemomvvmapicall.model

sealed class ConfigError : Throwable()
data class ConfigFormatError(val currentFormat : String) : ConfigError()
object WorkHorsNotMatch : ConfigError()