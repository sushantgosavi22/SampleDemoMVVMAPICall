package com.sushant.sampledemomvvmapicall.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sushant.sampledemomvvmapicall.databinding.ListItemBinding
import com.sushant.sampledemomvvmapicall.model.FeedItem

class FeedNewsViewHolder(var mListItemBinding : ListItemBinding) : BaseViewHolder<FeedItem>(mListItemBinding){
    override fun bind(model: FeedItem, position: Int, listenerI: ItemAdapter.IAdapterItemListener<FeedItem>?) {
        super.bind(model, position, listenerI)
        mListItemBinding.item = model
    }

    companion object{
        fun getInstance(parent: ViewGroup) : FeedNewsViewHolder{
            val binding =ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return FeedNewsViewHolder(binding)
        }
    }
}