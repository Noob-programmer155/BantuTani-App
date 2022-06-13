package com.bantutani.application.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bantutani.application.data.network.responses.commodity.CommodityGetAllResponseItem
import com.bantutani.application.databinding.PricelistExtBinding
import com.bumptech.glide.Glide

class CommodityAdapter :
    PagingDataAdapter<CommodityGetAllResponseItem, CommodityAdapter.ListViewHolder>(DIFF_CALLBACK) {
    class ListViewHolder(val binding: PricelistExtBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CommodityGetAllResponseItem) {
            data?.let {
                binding.apply {
                    Glide.with(itemView.context)
                        .load("http://104.196.207.218/api/public/commodity/v1/data/image/${it.icon}")
                        .into(comodityImage)
                    val now = it.currentCost!!-it.previousCost!!
                    tvName.text = it.name
                    tvPrice.text = "Rp. ${it.currentCost}"
                    if (it.previousCost<0){
                        tvProgress.text = it.currentCost.toString()
                    }else{
                        tvProgress.text = now.toString()
                    }
                    if (now > 0) {
                        ivProgress.visibility = View.GONE
                    } else {
                        ivProgress2.visibility = View.GONE
                    }
                }
            }
        }
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return CommodityAdapter.ListViewHolder(
            PricelistExtBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CommodityGetAllResponseItem>() {
            override fun areItemsTheSame(
                oldItem: CommodityGetAllResponseItem,
                newItem: CommodityGetAllResponseItem
            ): Boolean {
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