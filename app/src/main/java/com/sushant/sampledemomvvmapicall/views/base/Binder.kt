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
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.model.ListItemData
import com.sushant.sampledemomvvmapicall.views.details.viewmodel.DetailsViewModel
import java.io.File


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


    @BindingAdapter(value = ["bind:firstName", "bind:lastName"], requireAll = false)
    @JvmStatic
    public fun bindTitle(view: TextView, firstName: String?, lastName: String?) {
        view.text = Utils.getUserTitle(firstName, lastName)
    }


    @BindingAdapter(value = ["bind:buttonVisibility", "bind:viewModel"], requireAll = false)
    @JvmStatic
    public fun bindButtonVisibility(view: Button, item: Int?, viewModel: DetailsViewModel?) {
        val visible = viewModel?.shouldAddButtonVisible(item) ?: false
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @BindingAdapter(value = ["bind:itemId", "bind:viewModel"], requireAll = false)
    @JvmStatic
    public fun bindShouldEnable(view: EditText, item: Int?, viewModel: DetailsViewModel?) {
        val enable = viewModel?.shouldEnable(item) ?: false
        view.isEnabled = enable
        view.isFocusable = enable
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
    public fun bindTitle(view: TextView, customers: ListItemData?) {
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
    public fun bindAddress(view: TextView, customers: ListItemData?) {
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
}