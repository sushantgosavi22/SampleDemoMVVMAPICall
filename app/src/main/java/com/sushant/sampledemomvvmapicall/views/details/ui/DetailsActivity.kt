package com.sushant.sampledemomvvmapicall.views.details.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.databinding.ActivityDetailsBinding
import com.sushant.sampledemomvvmapicall.model.FeedItem
import com.sushant.sampledemomvvmapicall.views.base.BaseActivity
import com.sushant.sampledemomvvmapicall.views.details.viewmodel.DetailsViewModel

class DetailsActivity : BaseActivity() {
    private lateinit var mDetailsViewModel: DetailsViewModel
    private lateinit var binding: ActivityDetailsBinding
    val data: FeedItem
        get() {
            return intent?.getSerializableExtra(Utils.KEY_ITEM)
                ?.let { return it as FeedItem } ?: FeedItem()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        mDetailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        binding.item = data
    }

}