package com.sushant.sampledemomvvmapicall.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sushant.sampledemomvvmapicall.databinding.ListItemBinding
import com.sushant.sampledemomvvmapicall.model.ListItemData

class NewsViewHolder(var mListItemBinding : ListItemBinding) : BaseViewHolder<ListItemData>(mListItemBinding){
    override fun bind(model: ListItemData, position: Int, listenerI: ItemAdapter.IAdapterItemListener<ListItemData>?) {
        super.bind(model, position, listenerI)
        mListItemBinding.item = model
    }

    companion object{
        fun getInstance(parent: ViewGroup) : NewsViewHolder{
            val binding =ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return NewsViewHolder(binding)
        }
    }
}