package com.sushant.sampledemomvvmapicall.views.details.ui

import android.Manifest
import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.dhaval2404.imagepicker.ImagePicker
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.databinding.ActivityDetailsBinding
import com.sushant.sampledemomvvmapicall.model.FeedItem
import com.sushant.sampledemomvvmapicall.views.base.BaseActivity
import com.sushant.sampledemomvvmapicall.views.details.viewmodel.DetailsViewModel
import java.io.File

class DetailsActivity : BaseActivity(), IOnDoneClickListener, PermissionListener {
    private lateinit var mDetailsViewModel: DetailsViewModel
    lateinit var binding: ActivityDetailsBinding
    val data: FeedItem?
        get() {
            return intent?.getSerializableExtra(Utils.KEY_ITEM)
                ?.let { return it as FeedItem } ?: FeedItem()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        mDetailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        binding.viewModel = mDetailsViewModel
        binding.listener = this
        binding.item = data
    }

    override fun onDoneClick() {
        if (validate()) {
            showProgressBar()
            mDetailsViewModel.getSaveFeedCallBack().observe(this, Observer {
                hideProgressBar()
                if(it.data==true){
                    setResult(Activity.RESULT_OK)
                    finish()
                }else {
                    Utils.showToast(this, it.exception?.message?: getString(R.string.unexpected_error))
                }
            })
            mDetailsViewModel.saveFeed(binding.item)
        }
    }

    override fun onImageClick() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(this)
            .check()
    }


    private fun validate(): Boolean {
        val data = binding.item
        val result = data?.let {
            ((it.first_name?.isNotEmpty() == true) && it.last_name?.isNotEmpty() == true && it.email?.isNotEmpty() == true)
        } ?: false
        if (result.not()) {
            Utils.showToast(this, getString(R.string.validation_failed))
        }
        return result
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        ImagePicker.with(this)
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start { resultCode, data ->
                if (resultCode == Activity.RESULT_OK) {
                    val fileUri = data?.data
                    binding.image.setImageURI(fileUri)
                    val file: File? = ImagePicker.getFile(data)
                    val filePath: String? = file?.let { ImagePicker.getFilePath(data) }
                    binding.item?.avatar = filePath
                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    Utils.showToast(this, ImagePicker.getError(data))
                }
            }
    }

    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
        token?.continuePermissionRequest()
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Utils.showToast(this, getString(R.string.permission_required))
    }

}