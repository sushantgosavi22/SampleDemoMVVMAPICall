package com.sushant.sampledemomvvmapicall.views.details.ui

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.databinding.ActivityDetailsBinding
import com.sushant.sampledemomvvmapicall.model.Customers
import com.sushant.sampledemomvvmapicall.views.base.BaseActivity

class DetailsActivity : BaseActivity(), IOnFinishOrderClickListener {

    val data: Customers? get(){return intent?.getSerializableExtra(Utils.KEY_CUSTOMERS)?.let {return it as Customers}}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        data?.let {
            binding.responseItem = it
            binding.listener = this
        }
    }

    override fun onFinishOrderClick() {
        setResult(Activity.RESULT_OK)
        finish()
    }
}