package com.sushant.sampledemomvvmapicall.model

import java.io.Serializable

class DataResponse<T>(val status: Status, val response: BaseResponse<T>?, val error: Throwable?)  : Serializable  {
    companion object{
        fun <T>  loading(): DataResponse<T> {
            return DataResponse(Status.LOADING, null, null)
        }

        fun <T>  success(data: BaseResponse<T>?): DataResponse<T> {
            return DataResponse(Status.SUCCESS, data, null)
        }

        fun <T>  error(error: Throwable?): DataResponse<T> {
            return DataResponse(Status.ERROR, null, error)
        }

        fun <T>  configError(configError : ConfigError): DataResponse<T> {
            return DataResponse(Status.CONFIG_ERROR, null, configError)
        }

        fun <T>  clearListAndHideError(): DataResponse<T> {
            return DataResponse(Status.CLEAR_LIST_HIDE_ERROR, null, null)
        }

        fun <T>  emptyList(): DataResponse<T> {
            return DataResponse(Status.SHOW_EMPTY_LIST, null, null)
        }
    }
}