package com.sushant.sampledemomvvmapicall.views.details.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.constant.Constant
import com.sushant.sampledemomvvmapicall.databinding.ActivityDetailsBinding
import com.sushant.sampledemomvvmapicall.model.FeedItem
import com.sushant.sampledemomvvmapicall.views.base.BaseActivity
import com.sushant.sampledemomvvmapicall.views.details.viewmodel.DetailsViewModel

class DetailsActivity : BaseActivity() {
    private lateinit var mDetailsViewModel: DetailsViewModel
    private lateinit var binding: ActivityDetailsBinding

    private val data: FeedItem by lazy {
        intent?.getSerializableExtra(Constant.KEY_ITEM)?.let { it as FeedItem } ?: FeedItem()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        mDetailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        binding.item = data
        initToolbar()
        initView()
    }

    private fun initToolbar() {
        title = data.title
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.webViewClient = object : WebViewClient() {
            init {
                showProgressBar()
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                hideProgressBar()
                super.onPageFinished(view, url)
            }

            override fun onReceivedError(
                view: WebView?, request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                hideProgressBar()
                super.onReceivedError(view, request, error)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let { loadUrl->
                    view?.loadUrl(loadUrl)
                }
                return true
            }
        }
    }
}