package com.android.sampledemomvvmapicall.github.util

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
fun Activity.setStatusBarGradiant() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window: Window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this,android.R.color.transparent)
        window.navigationBarColor = ContextCompat.getColor(this,android.R.color.transparent)
        window.setBackgroundDrawableResource(android.R.color.white)
    }
}