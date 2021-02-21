package com.sushant.sampledemomvvmapicall.views.adapter

import android.annotation.SuppressLint
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil


abstract class BasePaginationAdapter<T> : PagedListAdapter<T, BaseViewHolder<T>?>(getDiffCallBack()) {
    companion object {
        fun <T> getDiffCallBack():  DiffUtil.ItemCallback<T> {
            return object : DiffUtil.ItemCallback<T>(){
                override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =oldItem.hashCode()==newItem.hashCode()

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem?.equals(newItem)==true
            }
        }
    }
}