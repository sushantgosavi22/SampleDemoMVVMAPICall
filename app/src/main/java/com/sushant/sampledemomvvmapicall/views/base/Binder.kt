package com.sushant.sampledemomvvmapicall.views.base

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sushant.sampledemomvvmapicall.R


object Binder {

    @BindingAdapter("bind:bindUrlImage")
    @JvmStatic
    public fun bindUrlImage(view: AppCompatImageView, url: String?) {
        url?.let {
            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.flower)
                .error(R.drawable.flower)
            Glide.with(view).load(url).apply(options).into(view)
        }
    }

    @BindingAdapter("bind:bindWebUrl")
    @JvmStatic
    public fun bindWebUrl(webView: WebView, url: String?) {
        url?.let {
            webView.loadUrl(url)
        }
    }
}