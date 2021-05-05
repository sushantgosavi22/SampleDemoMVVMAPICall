package com.sushant.sampledemomvvmapicall.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.sushant.sampledemomvvmapicall.databinding.UserItemBinding
import com.sushant.sampledemomvvmapicall.model.Users

class UserViewHolder(var mListItemBinding: UserItemBinding) :
    BaseViewHolder<Users>(mListItemBinding) {
    override fun bind(
        model: Users,
        position: Int,
        listenerI: ItemAdapter.IAdapterItemListener<Users>?,
        isSelectedItem: Boolean
    ) {
        super.bind(model, position, listenerI, isSelectedItem)
        mListItemBinding.item = model
        if (isSelectedItem) {
            mListItemBinding.tvHeadline.setBackgroundColor(
                ContextCompat.getColor(
                    mListItemBinding.root.context,
                    android.R.color.darker_gray
                )
            )
        } else {
            mListItemBinding.tvHeadline.setBackgroundColor(
                ContextCompat.getColor(
                    mListItemBinding.root.context,
                    android.R.color.white
                )
            )

        }
    }

    companion object {
        fun getInstance(parent: ViewGroup): UserViewHolder {
            val binding =
                UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return UserViewHolder(binding)
        }
    }
}