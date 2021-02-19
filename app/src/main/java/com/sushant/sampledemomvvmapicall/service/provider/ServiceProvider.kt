package com.sushant.sampledemomvvmapicall.service.provider

import android.content.Context
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.model.ProfilerResponse
import com.sushant.sampledemomvvmapicall.service.clients.APIClient
import com.sushant.sampledemomvvmapicall.service.interfaces.ApiInterface
import com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel.IListCallBack
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ServiceProvider : IServiceProvider{
    override fun getUsers(context : Context, page: Int, mIListCallBack: IListCallBack) {
        if (Utils.isInternetConnected(context)) {
            val apiServices = APIClient.client.create(ApiInterface::class.java)
            apiServices?.getUsers(page)?.enqueue(object : Callback<ProfilerResponse> {
                override fun onResponse(
                    call: Call<ProfilerResponse>,
                    response: Response<ProfilerResponse>
                ) {
                    val orderResponse = response.body()
                    if (orderResponse?.data != null && orderResponse.data?.isNotEmpty()==true) {
                        mIListCallBack.run {
                            this.onCallBack(
                                true,
                                orderResponse.data,
                                null
                            )
                        }
                    } else {
                        val error =context.getString(R.string.list_not_found)
                        mIListCallBack.onCallBack(
                            success = false,
                            data = null,
                            t = Throwable(error)
                        )
                    }
                }

                override fun onFailure(call: Call<ProfilerResponse>?, t: Throwable?) {
                    mIListCallBack.onCallBack(false, null, t)
                }
            })
        } else {
            val error = context.getString(R.string.no_internet_connection)
            mIListCallBack.onCallBack(false, null, Throwable(error))
        }
    }
}