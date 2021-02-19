package com.sushant.sampledemomvvmapicall.views.details.viewmodel

import com.sushant.sampledemomvvmapicall.model.ProfilerItemData

public interface ISaveUserCallback {
    fun onCallBack(success: Boolean, t: Throwable?)
}
