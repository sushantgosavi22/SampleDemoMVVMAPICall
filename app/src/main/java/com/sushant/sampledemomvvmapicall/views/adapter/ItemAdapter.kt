package com.sushant.sampledemomvvmapicall.views.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sushant.sampledemomvvmapicall.databinding.ListItemBinding
import com.sushant.sampledemomvvmapicall.model.ProfilerItemData
import java.util.*

class ItemAdapter(private var listener: IOnItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val itemList = ArrayList<ProfilerItemData>()

    fun setList(orderList: List<ProfilerItemData>) {
        this.itemList.clear()
        this.itemList.addAll(orderList)
        notifyDataSetChanged()
    }

    fun addItems(orderList: List<ProfilerItemData>) {
        this.itemList.addAll(orderList)
        notifyDataSetChanged()
    }

    fun updateItem(pos: Int, data: ProfilerItemData?) {
        data?.let {
            itemList[pos] = data
            notifyItemChanged(pos)
        }
    }

    fun insertItemAtTop(data: ProfilerItemData?) {
        data?.let {
            itemList.add(0,data)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val locationModel = itemList[position]
        (holder as ItemAdapter.RecyclerHolder).bind(locationModel, position, listener)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val applicationBinding = ListItemBinding.inflate(layoutInflater, parent, false)
        return RecyclerHolder(applicationBinding)
    }

    interface IOnItemClickListener {
        fun onOrderClick(pos: Int, data: ProfilerItemData?)
    }


    inner class RecyclerHolder(private var applicationBinding: ListItemBinding) :
        RecyclerView.ViewHolder(applicationBinding.root) {
        fun bind(appInfo: ProfilerItemData, position: Int, listenerI: IOnItemClickListener?) {
            applicationBinding.apply {
                item = appInfo
                applicationBinding.cvCardView.setOnClickListener {
                    listenerI?.onOrderClick(position, appInfo)
                }
            }
        }
    }
}