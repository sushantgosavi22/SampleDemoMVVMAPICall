package com.sushant.sampledemomvvmapicall.repositorys.userrepo

import android.app.Application
import android.content.Context
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.database.helper.DatabaseFailure
import com.sushant.sampledemomvvmapicall.database.helper.DatabaseResult
import com.sushant.sampledemomvvmapicall.database.helper.DatabaseSuccess
import com.sushant.sampledemomvvmapicall.database.helper.IDatabaseResultListener
import com.sushant.sampledemomvvmapicall.database.provider.DatabaseProvider
import com.sushant.sampledemomvvmapicall.database.provider.IDatabaseProvider
import com.sushant.sampledemomvvmapicall.model.ProfilerItemData
import com.sushant.sampledemomvvmapicall.model.ProfilerResponse
import com.sushant.sampledemomvvmapicall.service.clients.APIClient
import com.sushant.sampledemomvvmapicall.service.interfaces.ApiInterface
import com.sushant.sampledemomvvmapicall.service.provider.IServiceProvider
import com.sushant.sampledemomvvmapicall.service.provider.ServiceProvider
import com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel.DashboardViewModel
import com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel.IListCallBack
import com.sushant.sampledemomvvmapicall.views.details.viewmodel.ISaveUserCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository : IUserRepository{
    var mIDatabaseProvider  : IDatabaseProvider = DatabaseProvider
    var mIServiceProvider  : IServiceProvider = ServiceProvider


    override fun getUsers(context : Context, page: Int, mIListCallBack: IListCallBack) {
        mIDatabaseProvider.getUserListFromDatabase(object :IDatabaseResultListener<ProfilerResponse>{

            override fun onResult(databaseResult: DatabaseResult<ProfilerResponse>) {
                when(databaseResult){
                    is DatabaseFailure-> getUsersResponse(context,page,mIListCallBack)
                    is DatabaseSuccess-> mIListCallBack.onCallBack(true, databaseResult.data.data,null)
                }
            }
        })
    }

    private fun getUsersResponse(context : Context, page: Int, mIListCallBack: IListCallBack) {
        mIServiceProvider.getUsers(context,page,object : IListCallBack{
            override fun onCallBack(success: Boolean, data: List<ProfilerItemData>?, t: Throwable?) {
                data?.forEach {
                    saveUser(it,object :ISaveUserCallback{
                        override fun onCallBack(success: Boolean, t: Throwable?) {}
                    })
                }
                mIListCallBack.onCallBack(success,data,t)
            }
        })
    }


    override fun saveUser(data : ProfilerItemData?, mISaveUserCallback: ISaveUserCallback) {
        mIDatabaseProvider.saveUser(object :IDatabaseResultListener<Boolean>{
            override fun onResult(databaseResult: DatabaseResult<Boolean>) {
                when(databaseResult){
                    is DatabaseFailure-> mISaveUserCallback.onCallBack(false, databaseResult.httpError.throwable)
                    is DatabaseSuccess-> mISaveUserCallback.onCallBack(true,null)
                }
            }
        },data)
    }
}