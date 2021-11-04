package com.sushant.sampledemomvvmapicall.views.adapter


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter<T, out H : BaseViewHolder<T>>(val list : ArrayList<T>, private var listener: IAdapterItemListener<T>) :  RecyclerView.Adapter<BaseViewHolder<T>>()  {

    override fun getItemCount(): Int =list.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> = listener.getHolder(parent)
    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        list[position]?.let { (holder as H).bind(it, position, listener) }
    }

    fun setList(mList : ArrayList<T>){
        list.clear()
        list.addAll(mList)
        notifyDataSetChanged()
    }

    fun updateList(mList: ArrayList<T>) {
        val start = list.size
        list.addAll(mList)
        notifyItemRangeChanged(start, list.size)
    }

    fun updateItem(index : Int,item : T){
        list[index] = item
        notifyItemChanged(index)
    }

    fun addItem(item : T){
        list.add(item)
        notifyItemInserted(list.size)
    }

    fun getSearchableList() : ArrayList<T> = ArrayList(list)

    interface IAdapterItemListener<T> {
        fun onItemClick(pos: Int, data: T?)
        fun getHolder(parent: ViewGroup):BaseViewHolder<T>
    }

}