package com.sushant.sampledemomvvmapicall.database.helper

/*sealed class DatabaseResult
class DatabaseSuccess(response : Any?) : DatabaseResult()
class DatabaseFailure(error : Throwable) : DatabaseResult()*/
const val EC_ENABLE_TO_SAVE_USER = 1
const val EC_NULL_USER_OBJECT_GIVEN = 2
const val EC_EMPTY_LIST_IN_DATABASE = 3
class HttpError(val throwable: Throwable, val errorCode: Int = 0)
sealed class DatabaseResult<out T : Any>
data class DatabaseSuccess<out T : Any>(val data: T) : DatabaseResult<T>()
data class DatabaseFailure(val httpError: HttpError) : DatabaseResult<Nothing>()


inline fun <T : Any> DatabaseResult<T>.onSuccess(action: (T) -> Unit): DatabaseResult<T> {
    if (this is DatabaseSuccess) action(data)
    return this
}

inline fun <T : Any> DatabaseResult<T>.onFailure(action: (HttpError) -> Unit) {
    if (this is DatabaseFailure) action(httpError)
}
