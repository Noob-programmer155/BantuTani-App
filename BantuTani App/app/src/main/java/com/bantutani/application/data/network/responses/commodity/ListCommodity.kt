package com.bantutani.application.data.network.responses.commodity


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bantutani.application.databinding.PricelistExtBinding
import com.bumptech.glide.Glide

class ListCommodity: PagingDataAdapter<CommodityGetAllResponseItem, ListCommodity.ViewHolder>(DIFF_CALLBACK) {

    private val listCommodity = ArrayList<CommodityGetAllResponseItem>()

    fun setList(commodity: ArrayList<CommodityGetAllResponseItem>) {
        listCommodity.clear()
        listCommodity.addAll(commodity)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: PricelistExtBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(commodity: CommodityGetAllResponseItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(commodity.icon)
                    .into(comodityImage)
                tvName.text = commodity.name
                tvPrice.text = commodity.currentCost.toString()
                tvProgress.text = commodity.costIncrease.toString()
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCommodity.ViewHolder {
        val view = PricelistExtBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listCommodity = getItem(position)
        if (listCommodity != null) {
            holder.bind(listCommodity)
        }
    }

    override fun getItemCount(): Int = listCommodity.size


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CommodityGetAllResponseItem>() {
            override fun areItemsTheSame(oldItem: CommodityGetAllResponseItem, newItem: CommodityGetAllResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CommodityGetAllResponseItem,
                newItem: CommodityGetAllResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}