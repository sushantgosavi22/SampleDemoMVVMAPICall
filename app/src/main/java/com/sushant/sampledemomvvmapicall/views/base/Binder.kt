package com.sushant.sampledemomvvmapicall.views.base

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.sushant.sampledemomvvmapicall.R
import java.io.File


object Binder {

    @BindingAdapter("bind:loadUrl")
    @JvmStatic
    public fun bindUrlImage(view: ImageView, url: String?) {
        url?.let {
            if(url.contains("http")){
                Glide.with(view)
                    .load(url)
                    .placeholder(R.drawable.flower)
                    .error(R.drawable.flower)
                    .into(view)
            }else{
                Glide.with(view)
                    .load(Uri.fromFile(File(url)))
                    .placeholder(R.drawable.flower)
                    .error(R.drawable.flower)
                    .into(view)
            }
        }
    }


    @BindingAdapter(value = ["bind:firstName", "bind:lastName"], requireAll = false)
    @JvmStatic
    public fun bindTitle(view: TextView, firstName : String?, lastName : String?) {
        var result: String = firstName?.plus(" ".plus(lastName?:""))?:lastName?:""
        view.text = result
    }




    @BindingAdapter("bind:buttonVisibility")
    @JvmStatic
    public fun bindButtonVisibility(view: Button, item: Int?) {
        view.visibility =if(item==null)View.VISIBLE else View.GONE
    }

    @BindingAdapter("bind:viewEnable")
    @JvmStatic
    public fun bindViewEnable(view: EditText, item: Int?) {
        view.isEnabled =(item==null)
        view.isFocusable = (item==null)
    }
}