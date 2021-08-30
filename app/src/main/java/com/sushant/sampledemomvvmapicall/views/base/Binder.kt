package com.sushant.sampledemomvvmapicall.views.base

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.application.App
import com.sushant.sampledemomvvmapicall.constant.Utils
import java.io.File


object Binder {

    @BindingAdapter("bind:type")
    @JvmStatic
    public fun bindType(view: TextView, type: String?) {
        view.text = type?.capitalize()?:""
    }
}