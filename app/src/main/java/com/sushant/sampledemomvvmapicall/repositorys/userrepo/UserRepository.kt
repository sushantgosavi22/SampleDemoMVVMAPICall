package com.sushant.sampledemomvvmapicall.repositorys.userrepo

import android.content.Context
import com.sushant.sampledemomvvmapicall.database.provider.DatabaseProvider
import com.sushant.sampledemomvvmapicall.database.provider.IDatabaseProvider
import com.sushant.sampledemomvvmapicall.model.ListItemData
import com.sushant.sampledemomvvmapicall.model.ResponseModel
import com.sushant.sampledemomvvmapicall.service.provider.IServiceProvider
import com.sushant.sampledemomvvmapicall.service.provider.ServiceProvider
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver

class UserRepository : IUserRepository {
    var mIDatabaseProvider: IDatabaseProvider = DatabaseProvider
    var mIServiceProvider: IServiceProvider = ServiceProvider


    override fun getOrederList(context: Context, page: Int): Single<ResponseModel> {
        /**
         * In case of offline storage we will check and get data from
         * Database or Service from here as per condition
         *
         */
        //return mIDatabaseProvider.getUsersFromDatabase()

        return getOrderList(context, page)
    }

    private fun getOrderList(context: Context, page: Int): Single<ResponseModel> {
        return mIServiceProvider.gerOrderList(context, page).flatMap {
            /**
             * In the case if want to save api response into local database
             */
           /* it.customers?.forEach {
                saveItem(it).subscribe(object : DisposableSingleObserver<Boolean>() {
                        override fun onSuccess(t: Boolean) {}
                        override fun onError(e: Throwable) {}
                    })
            }*/
            Single.just(it)
        }
    }


    override fun saveItem(data: ListItemData?): Single<Boolean> {
        return mIDatabaseProvider.saveItem(data)
    }
}