package com.sushant.sampledemomvvmapicall.service.model

public class ApiResponse<T>  {
    var status: Status? = null
    var error: Throwable? = null
    var response: BaseResponse<T>? = null

    private constructor(status: Status, response: BaseResponse<T>?, error: Throwable?) {
        this.status = status
        this.response = response
        this.error = error
    }

    companion object{
        fun <T>  loading(): ApiResponse<T>? {
            return ApiResponse(Status.LOADING, null, null)
        }

        fun <T>  success(data: BaseResponse<T>?): ApiResponse<T>? {
            return ApiResponse(Status.SUCCESS, data, null)
        }

        fun <T>  error(error: Throwable?): ApiResponse<T>? {
            return ApiResponse(Status.ERROR, null, error)
        }

        fun <T>  clearListAndHideError(): ApiResponse<T>? {
            return ApiResponse(Status.CLEAR_LIST_HIDE_ERROR, null, null)
        }

        fun <T>  emptyList(): ApiResponse<T>? {
            return ApiResponse(Status.SHOW_EMPTY_LIST, null, null)
        }


    }
}