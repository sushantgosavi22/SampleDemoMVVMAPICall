package com.sushant.sampledemomvvmapicall.constant

import android.app.Activity
import android.text.format.Time.SUNDAY
import android.widget.Toast
import java.util.*
import kotlin.time.days

object Constant {

    const val KEY_ITEM: String = "key_item"
    const val PERSISTED: String = "persisted"
    const val RESPONSE: String = "response"
    const val WORKING_HOUR_KEY: String = "working"
    const val PET_LIST_JSON_FILE: String = "pets_list.json"
    const val CONFIG_JSON_FILE: String = "config.json"
    const val FIRST_PAGE: Int = 1
    const val DAY_INDEX: Int = 0
    const val FROM_TIME_INDEX: Int = 1
    const val TO_TIME_INDEX: Int = 3
}

fun Activity.showToast(message: String?) {
    message?.let {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}

val dayList: Map<String, Int> by lazy {
    hashMapOf<String, Int>().apply {
        put("S",1)
        put("M",2)
        put("T",3)
        put("W",4)
        put("TH",5)
        put("F",6)
        put("SA",7)
    }
}