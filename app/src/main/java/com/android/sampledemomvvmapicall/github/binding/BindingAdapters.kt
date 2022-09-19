package com.android.sampledemomvvmapicall.github.binding

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * Data Binding adapters specific to the app.
 */
object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @SuppressLint("CheckResult")
    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(view: ImageView, url: String?) {
        /*url?.let {
            val requestOptions = RequestOptions()
            requestOptions.placeholder(R.drawable.ic_baseline_app_icon)
            requestOptions.error(R.drawable.ic_baseline_app_icon)
            Glide.with(view)
                .setDefaultRequestOptions(requestOptions)
                .load(Uri.fromFile(File(url)))
                .into(view)
        }*/
        url?.let {
            Glide.with(view).load(url).into(view)
        }
    }
}
