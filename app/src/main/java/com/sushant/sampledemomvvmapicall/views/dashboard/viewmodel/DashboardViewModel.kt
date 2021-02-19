package com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sushant.sampledemomvvmapicall.repositorys.userrepo.IUserRepository
import com.sushant.sampledemomvvmapicall.repositorys.userrepo.UserRepository

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    var mIUserRepository : IUserRepository = UserRepository()

    fun getUsers(page: Int, mIListCallBack: IListCallBack) {
          mIUserRepository.getUsers(getApplication(),page,mIListCallBack)
    }
}