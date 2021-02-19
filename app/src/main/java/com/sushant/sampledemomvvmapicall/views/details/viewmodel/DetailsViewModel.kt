package com.sushant.sampledemomvvmapicall.views.details.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sushant.sampledemomvvmapicall.model.ProfilerItemData
import com.sushant.sampledemomvvmapicall.repositorys.userrepo.IUserRepository
import com.sushant.sampledemomvvmapicall.repositorys.userrepo.UserRepository

class DetailsViewModel(application: Application) : AndroidViewModel(application) {
    var mIUserRepository : IUserRepository = UserRepository()


    fun saveUser(data : ProfilerItemData?, mISaveUserCallback: ISaveUserCallback){
        mIUserRepository.saveUser(data,mISaveUserCallback)
    }

}