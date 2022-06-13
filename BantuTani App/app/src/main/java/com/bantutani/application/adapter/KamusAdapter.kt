package com.bantutani.application.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bantutani.application.data.network.responses.plants.GetAllPlantsResponseItem
import com.bantutani.application.databinding.KamusCardBinding
import com.bumptech.glide.Glide

class KamusAdapter: PagingDataAdapter<GetAllPlantsResponseItem, KamusAdapter.ListViewHolder>(KamusAdapter.DIFF_CALLBACK) {
    class ListViewHolder(val binding: KamusCardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: GetAllPlantsResponseItem?){
            data?.let {
                binding.apply {
                    Glide.with(itemView.context)
                        .load("https://104.196.207.218/api/v1/news/image/${it.image}")
                        .override(350, 167)
                        .into(plantImage)
                    plantNama.text = data.image
                }
            }
        }
    }

    override fun onBindViewHolder(holder: KamusAdapter.ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KamusAdapter.ListViewHolder {
        return KamusAdapter.ListViewHolder(
            KamusCardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GetAllPlantsResponseItem>() {
            override fun areItemsTheSame(oldItem: GetAllPlantsResponseItem, newItem: GetAllPlantsResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: GetAllPlantsResponseItem,
                newItem: GetAllPlantsResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}