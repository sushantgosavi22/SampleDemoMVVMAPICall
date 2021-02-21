package com.sushant.sampledemomvvmapicall.views.details.ui

import android.Manifest
import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
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
import com.sushant.sampledemomvvmapicall.model.ListItemData
import com.sushant.sampledemomvvmapicall.views.base.BaseActivity
import com.sushant.sampledemomvvmapicall.views.details.viewmodel.DetailsViewModel
import java.io.File

class DetailsActivity : BaseActivity(), IOnDoneClickListener, PermissionListener {
    private lateinit var mDetailsViewModel: DetailsViewModel
    lateinit var binding: ActivityDetailsBinding
    val data: ListItemData?
        get() {
            return intent?.getSerializableExtra(Utils.KEY_ITEM)
                ?.let { return it as ListItemData } ?: ListItemData()
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
        setResult(Activity.RESULT_OK)
        finish()
        /**
         * This is if want to save user in local database
         * in the case that active application
         */
       /* showProgressBar()
        mDetailsViewModel.getSaveCallBack().observe(this, Observer {
            hideProgressBar()
            if (it != null) {
                Utils.showToast(this, it.message ?: getString(R.string.unexpected_error))
            } else {
                setResult(Activity.RESULT_OK)
                finish()
            }
        })
        mDetailsViewModel.saveItem(binding.item)*/
    }




    /**
     * This method used for asking user permission.
     */
    override fun onImageClick() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(this)
            .check()
    }


    /**
     * On permission callback.
     */
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
                    binding.item?.imageUrl = filePath
                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    Utils.showToast(this, ImagePicker.getError(data))
                }
            }
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {
        token?.continuePermissionRequest()
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Utils.showToast(this, getString(R.string.permission_required))
    }

}