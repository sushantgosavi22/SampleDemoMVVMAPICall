package com.sushant.sampledemomvvmapicall.views.adapter.pagination

import androidx.paging.PageKeyedDataSource
import com.sushant.sampledemomvvmapicall.model.ListItemData

sealed class PageLoadingCallBack(val pageValue: Int)
class LoadInitial(val page: Int, val callback: PageKeyedDataSource.LoadInitialCallback<Int, ListItemData>) : PageLoadingCallBack(page)
class LoadBefore(val page: Int, val callback: PageKeyedDataSource.LoadCallback<Int, ListItemData>) : PageLoadingCallBack(page)
class LoadAfter(val page: Int, val callback: PageKeyedDataSource.LoadCallback<Int, ListItemData>) : PageLoadingCallBack(page)