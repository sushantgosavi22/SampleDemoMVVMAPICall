package com.sushant.sampledemomvvmapicall.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sushant.sampledemomvvmapicall.databinding.ListItemBinding
import com.sushant.sampledemomvvmapicall.model.ProfilerItemData

class NewsViewHolder(var mListItemBinding : ListItemBinding) : BaseViewHolder<ProfilerItemData>(mListItemBinding){
    override fun bind(model: ProfilerItemData, position: Int, listenerI: ItemAdapter.IAdapterItemListener<ProfilerItemData>?) {
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