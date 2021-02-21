package com.sushant.sampledemomvvmapicall.views.adapter.pagination

import androidx.paging.PageKeyedDataSource
import com.sushant.sampledemomvvmapicall.model.ProfilerItemData

sealed class PageLoadingCallBack(val pageValue: Int)
class LoadInitial(val page: Int, val callback: PageKeyedDataSource.LoadInitialCallback<Int, ProfilerItemData>) : PageLoadingCallBack(page)
class LoadBefore(val page: Int, val callback: PageKeyedDataSource.LoadCallback<Int, ProfilerItemData>) : PageLoadingCallBack(page)
class LoadAfter(val page: Int, val callback: PageKeyedDataSource.LoadCallback<Int, ProfilerItemData>) : PageLoadingCallBack(page)