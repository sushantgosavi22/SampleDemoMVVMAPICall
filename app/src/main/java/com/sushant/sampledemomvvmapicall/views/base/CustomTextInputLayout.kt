package com.sushant.sampledemomvvmapicall.views.base

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.setPadding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sushant.sampledemomvvmapicall.R

open class CustomTextInputLayout : TextInputLayout {

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs,0) {
        init(context,attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?){

    }
}