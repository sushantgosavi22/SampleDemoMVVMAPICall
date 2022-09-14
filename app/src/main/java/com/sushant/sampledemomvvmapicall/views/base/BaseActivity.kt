package com.sushant.sampledemomvvmapicall.views.base

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sushant.sampledemomvvmapicall.R

open class BaseActivity : AppCompatActivity() {
    private var dialog: AlertDialog? = null
    /**
     * Show progress bar.
     */
    fun showProgressBar(){
        if (dialog == null)
            dialog = AlertDialog.Builder(this).setCancelable(false).setView(R.layout.progress_dailog_layout).create()
        dialog?.show()
    }

    /**
     * Hide progress bar.
     */
    fun hideProgressBar(){
        if (dialog != null && dialog?.isShowing==true)
            dialog?.hide()
    }
}