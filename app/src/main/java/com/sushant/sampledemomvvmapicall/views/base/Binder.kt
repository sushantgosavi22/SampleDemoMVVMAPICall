package com.sushant.sampledemomvvmapicall.views.base

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.model.Customers

object Binder {

    @BindingAdapter("bind:loadUrl")
    @JvmStatic
    public fun bindUrlImage(view: ImageView, url: String?) {
        url?.let {
            Glide.with(view)
                .load(url)
                .placeholder(R.drawable.flower)
                .error(R.drawable.flower)
                .into(view)
        }
    }

    @BindingAdapter("bind:loadActive")
    @JvmStatic
    public fun bindBookmarkImage(view: ImageView, bookmarked: Boolean?) {
        bookmarked?.let {
            view.setBackgroundResource(
                if (it) {
                    R.drawable.red_dot
                } else {
                    R.drawable.green_dot
                }
            )
        }
    }

    @BindingAdapter("bind:loadTitle")
    @JvmStatic
    public fun bindTitle(view: TextView, customers: Customers?) {
        customers?.let {
            var result: String = ""
            val customer = customers.customer
            val firstName = customer?.firstName
            val lastName = customer?.lastName
            result = firstName?.plus(" ").plus(lastName)
            view.text = result
        }
    }

    @BindingAdapter("bind:loadAddress")
    @JvmStatic
    public fun bindAddress(view: TextView, customers: Customers?) {
        customers?.let {
            var result: String = ""
            val customer = customers.customer
            val address = customer?.address
            val city = customer?.city
            val state = customer?.state
            val zip = customer?.zip
            result = address?.plus(", ").plus(city).plus(", ").plus(state).plus(" ").plus(zip)
            view.text = result
        }
    }

    @BindingAdapter("bind:loadScheduleDate")
    @JvmStatic
    public fun bindScheduleDate(view: TextView, string: String?) {
        string?.let {
            view.text = Utils.getLocalFormatterDate(Utils.getScheduleTime(string))
        }
    }

    @BindingAdapter("bind:loadScheduleEndTime")
    @JvmStatic
    public fun bindScheduleEndTime(view: TextView, string: String?) {
        string?.let {
            view.text = Utils.getLocalFormattedTime(string)
        }
    }
}