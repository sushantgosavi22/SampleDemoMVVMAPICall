package com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.model.Customers
import com.sushant.sampledemomvvmapicall.model.OrderResponse
import com.sushant.sampledemomvvmapicall.service.clients.APIClient
import com.sushant.sampledemomvvmapicall.service.interfaces.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    fun getCustomers(mICustomerListCallBack: ICustomerListCallBack) {
        if (Utils.isInternetConnected(getApplication())) {
            val apiServices = APIClient.client.create(ApiInterface::class.java)
            apiServices?.getOrderList()?.enqueue(object : Callback<OrderResponse> {
                override fun onResponse(
                    call: Call<OrderResponse>,
                    response: Response<OrderResponse>
                ) {
                    val orderResponse = response.body()
                    if (orderResponse?.customers != null && orderResponse.customers.isNotEmpty()) {
                        mICustomerListCallBack.run {
                            this.onCallBack(
                                true,
                                orderResponse.customers,
                                null
                            )
                        }
                    } else {
                        val error = getApplication<Application>().getString(R.string.list_not_found)
                        mICustomerListCallBack.onCallBack(
                            success = false,
                            data = null,
                            t = Throwable(error)
                        )
                    }
                }

                override fun onFailure(call: Call<OrderResponse>?, t: Throwable?) {
                    mICustomerListCallBack.onCallBack(false, null, t)
                }
            })
        } else {
            val error = getApplication<Application>().getString(R.string.no_internet_connection)
            mICustomerListCallBack.onCallBack(false, null, Throwable(error))
        }
    }

    interface ICustomerListCallBack {
        fun onCallBack(success: Boolean, data: List<Customers>?, t: Throwable?)
    }

}