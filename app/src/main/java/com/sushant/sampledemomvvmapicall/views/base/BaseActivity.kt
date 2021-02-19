package com.sushant.sampledemomvvmapicall.views.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import com.sushant.sampledemomvvmapicall.R
import kotlinx.android.synthetic.main.activity_dashboard.*

open class BaseActivity : AppCompatActivity() {

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
        progressBar?.visibility=View.VISIBLE
    }

    fun hideProgressBar(){
        progressBar?.visibility=View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}