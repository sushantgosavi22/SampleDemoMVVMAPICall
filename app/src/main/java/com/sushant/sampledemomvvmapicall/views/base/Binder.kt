package com.sushant.sampledemomvvmapicall.views.base

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.constant.Utils
import java.io.File
import java.text.DecimalFormat


object Binder {

    @BindingAdapter("bind:loadUrl")
    @JvmStatic
    public fun bindUrlImage(view: ImageView, url: String?) {
        url?.let {
            Glide.with(view)
                .load(if (url.contains("http")) url else Uri.fromFile(File(url)))
                .placeholder(R.drawable.flower)
                .error(R.drawable.flower)
                .into(view)
        }
    }


    @BindingAdapter("bind:bindPrice")
    @JvmStatic
    public fun bindPrice(view: TextView, value: String?) {
        if(!value.isNullOrEmpty()){
            view.text = String.format("%s%.2f","$", value.toFloat())
        }
    }

    @BindingAdapter("bind:bind24Hour")
    @JvmStatic
    public fun bind24Hour(view: TextView, value: String?) {
        if(!value.isNullOrEmpty()){
            view.text = String.format("%.2f%s", value.toFloat(),"%")
        }
    }
}