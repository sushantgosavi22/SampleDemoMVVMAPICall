package com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel

import com.sushant.sampledemomvvmapicall.model.ProfilerItemData

public interface IListCallBack {
    fun onCallBack(success: Boolean, data: List<ProfilerItemData>?, t: Throwable?)
}
