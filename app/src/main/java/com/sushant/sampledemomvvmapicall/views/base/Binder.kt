package com.sushant.sampledemomvvmapicall.views.base

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sushant.sampledemomvvmapicall.R
import java.text.DecimalFormat
import java.util.regex.Matcher
import java.util.regex.Pattern


object Binder {

    @BindingAdapter("bind:loadColor")
    @JvmStatic
    public fun bindColor(view: View, aqi: Double?) {
        val color = when (aqi?.toInt()) {
            in 0..50 -> R.color.good_color
            in 51..100 -> R.color.satisfactory_color
            in 101..200 -> R.color.moderate_color
            in 201..300 -> R.color.poor_color
            in 301..400 -> R.color.very_poor_color
            in 401..500 -> R.color.severe_color
            else -> R.color.severe_color
        }
        view.setBackgroundResource(color)
    }

    @BindingAdapter("bind:bindAqi")
    @JvmStatic
    public fun bindAqi(view: TextView, aqi: Double?) {
        view.text = DecimalFormat("#.##").format(aqi)
    }
}