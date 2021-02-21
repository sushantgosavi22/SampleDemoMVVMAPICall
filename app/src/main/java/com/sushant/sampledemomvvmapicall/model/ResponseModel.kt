package com.sushant.sampledemomvvmapicall.model

import com.sushant.sampledemomvvmapicall.service.model.BaseResponse

open class ResponseModel : BaseResponse<ResponseModel>() {
    var customers: List<ListItemData>? = null
}
