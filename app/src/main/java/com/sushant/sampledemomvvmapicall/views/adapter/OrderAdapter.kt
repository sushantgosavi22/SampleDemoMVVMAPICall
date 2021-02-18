package com.sushant.sampledemomvvmapicall.views.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.databinding.OrderItemBinding
import com.sushant.sampledemomvvmapicall.model.Customers
import java.util.*

class OrderAdapter(private var listenerI: IOnOrderClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val orderList = ArrayList<Customers>()

    fun setList(orderList: List<Customers>) {
        this.orderList.clear()
        this.orderList.addAll(orderList)
        notifyDataSetChanged()
    }

    fun updateItem(pos: Int,data : Customers?){
        data?.let {
            orderList[pos] = data
            notifyItemChanged(pos)
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val locationModel = orderList[position]
        (holder as OrderAdapter.RecyclerHolder).bind(locationModel,position, listenerI)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val applicationBinding = OrderItemBinding.inflate(layoutInflater, parent, false)
        return RecyclerHolder(applicationBinding)
    }

    interface IOnOrderClickListener {
        fun onOrderClick(pos: Int,data: Customers?)
    }


    inner class RecyclerHolder(private var applicationBinding: OrderItemBinding) : RecyclerView.ViewHolder(applicationBinding.root) {
        fun bind(appInfo: Customers, position: Int, listenerI: IOnOrderClickListener?) {
            applicationBinding.apply {
                responseItem = appInfo
                itemCard.setOnClickListener {
                    listenerI?.onOrderClick(position,appInfo)
                }
            }
        }
    }
}