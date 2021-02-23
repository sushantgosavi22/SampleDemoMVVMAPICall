package com.sushant.sampledemomvvmapicall.database.helper

const val EC_EMPTY_LIST_IN_DATABASE = 3
class HttpError(val throwable: Throwable, val errorCode: Int = 0)
sealed class DatabaseResult<out T : Any>
data class DatabaseSuccess<out T : Any>(val data: T) : DatabaseResult<T>()
data class DatabaseFailure(val httpError: HttpError) : DatabaseResult<Nothing>()

