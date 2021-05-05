package com.sushant.sampledemomvvmapicall.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sushant.sampledemomvvmapicall.databinding.CircleItemBinding
import com.sushant.sampledemomvvmapicall.model.Circles

class CircleViewHolder(var mListItemBinding: CircleItemBinding) :
    BaseViewHolder<Circles>(mListItemBinding) {

    override fun bind(
        model: Circles,
        position: Int,
        listenerI: ItemAdapter.IAdapterItemListener<Circles>?,
        isSelectedItem: Boolean
    ) {
        super.bind(model, position, listenerI, isSelectedItem)
        mListItemBinding.item = model
    }
    companion object {
        fun getInstance(parent: ViewGroup): CircleViewHolder {
            val binding =
                CircleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CircleViewHolder(binding)
        }
    }
}