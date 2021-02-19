package com.sushant.sampledemomvvmapicall.model

data class ProfilerResponse(
    var data: List<ProfilerItemData>? = null,
    var page: Int? = null,
    var per_page: Int? = null,
    var total: Int? = null,
    var total_pages: Int? = null
)