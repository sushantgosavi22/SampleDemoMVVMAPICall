package com.sushant.sampledemomvvmapicall.views.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.sushant.sampledemomvvmapicall.R

open class BaseActivity : AppCompatActivity() {
    private var dialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpActionBar()
    }

    private fun setUpActionBar() {
        supportActionBar?.apply {
            this.setHomeAsUpIndicator(ContextCompat.getDrawable(this@BaseActivity,R.drawable.back_arrow))
            this.setHomeButtonEnabled(true)
            this.setDisplayHomeAsUpEnabled(true)
            this.setDisplayShowHomeEnabled(true)
            this.setDisplayHomeAsUpEnabled(true)
            this.setIcon(ContextCompat.getDrawable( this@BaseActivity,R.drawable.app_icon_profiler))
        }

    }

    fun showProgressBar(){
        if (dialog == null)
            dialog = AlertDialog.Builder(this).setCancelable(false).setView(R.layout.progress_dailog_layout).create()
        dialog?.show()
    }

    fun hideProgressBar(){
        if (dialog != null && dialog?.isShowing==true)
            dialog?.hide()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}