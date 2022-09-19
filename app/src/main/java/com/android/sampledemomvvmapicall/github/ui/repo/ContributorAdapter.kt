package com.android.sampledemomvvmapicall.github.ui.repo

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.android.sampledemomvvmapicall.github.AppExecutors
import com.android.sampledemomvvmapicall.github.R
import com.android.sampledemomvvmapicall.github.databinding.ContributorItemBinding
import com.android.sampledemomvvmapicall.github.ui.common.DataBoundListAdapter
import com.android.sampledemomvvmapicall.github.vo.Contributor

class ContributorAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((Contributor, ImageView) -> Unit)?
) : DataBoundListAdapter<Contributor, ContributorItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Contributor>() {
        override fun areItemsTheSame(oldItem: Contributor, newItem: Contributor): Boolean {
            return oldItem.login == newItem.login
        }

        override fun areContentsTheSame(oldItem: Contributor, newItem: Contributor): Boolean {
            return oldItem.avatarUrl == newItem.avatarUrl
                    && oldItem.contributions == newItem.contributions
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ContributorItemBinding {
        val binding = DataBindingUtil
            .inflate<ContributorItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.contributor_item,
                parent,
                false,
                dataBindingComponent
            )
        binding.root.setOnClickListener {
            binding.contributor?.let {
                callback?.invoke(it, binding.imageView)
            }
        }
        return binding
    }

    override fun bind(binding: ContributorItemBinding, item: Contributor) {
        binding.contributor = item
    }
}
