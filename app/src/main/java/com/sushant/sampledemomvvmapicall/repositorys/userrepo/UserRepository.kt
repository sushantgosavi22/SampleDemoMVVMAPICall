package com.sushant.sampledemomvvmapicall.repositorys.userrepo

import android.content.Context
import android.util.Log
import com.sushant.sampledemomvvmapicall.database.helper.DatabaseFailure
import com.sushant.sampledemomvvmapicall.database.helper.DatabaseSuccess
import com.sushant.sampledemomvvmapicall.database.provider.DatabaseProvider
import com.sushant.sampledemomvvmapicall.database.provider.IDatabaseProvider
import com.sushant.sampledemomvvmapicall.model.ProfilerItemData
import com.sushant.sampledemomvvmapicall.model.ProfilerResponse
import com.sushant.sampledemomvvmapicall.service.provider.IServiceProvider
import com.sushant.sampledemomvvmapicall.service.provider.ServiceProvider
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

class UserRepository : IUserRepository {
    var mIDatabaseProvider: IDatabaseProvider = DatabaseProvider
    var mIServiceProvider: IServiceProvider = ServiceProvider


    override fun getUsers(context: Context, page: Int): Single<ProfilerResponse> {
        return mIDatabaseProvider.getUsersFromDatabase()
            .flatMap {
                when (it) {
                    is DatabaseFailure -> getUsersResponse(context, page)
                    is DatabaseSuccess -> Single.just(it.data)
                }
            }
    }

    private fun getUsersResponse(context: Context, page: Int): Single<ProfilerResponse> {
        return mIServiceProvider.getUsers(context, page).flatMap {
            it.data?.forEach {
                saveUser(it).subscribe(object : DisposableSingleObserver<Boolean>() {
                        override fun onSuccess(t: Boolean) {}
                        override fun onError(e: Throwable) {}
                    })
            }
            Single.just(it)
        }
    }


    override fun saveUser(data: ProfilerItemData?): Single<Boolean> {
        return mIDatabaseProvider.saveUser(data)
    }
}