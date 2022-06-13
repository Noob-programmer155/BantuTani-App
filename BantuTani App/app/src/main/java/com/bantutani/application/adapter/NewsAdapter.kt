package com.bantutani.application.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bantutani.application.data.entitiy.news.DataNews
import com.bantutani.application.data.network.responses.news.NewsGetResponseItem
import com.bantutani.application.databinding.NewsCardExtBinding
import com.bantutani.application.ui.newsdetail.NewsDetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders


class NewsAdapter(token: String) :
    PagingDataAdapter<NewsGetResponseItem, NewsAdapter.ListViewHolder>(DIFF_CALLBACK) {
    private var onItemClickCallback: OnItemClickCallback? = null
    val userToken = token

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: NewsGetResponseItem)
    }

    class ListViewHolder(val binding: NewsCardExtBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(token:String, data: NewsGetResponseItem?) {
            data?.let {
                binding.apply {
                    val glideUrl = GlideUrl(
                        "http://104.196.207.218/api/public/news/v1/media/${data.image}",
                        LazyHeaders.Builder()
                            .addHeader("Accept", "*/*")
                            .build()
                    )
                    Log.e("token", token)
                    Glide.with(itemView.context)
                        .load(glideUrl)
                        .override(350, 167)
                        .into(newsImage)
                    newsExt.text = data.title
                }
                binding.root.setOnClickListener {
                    Log.e("idnews", data.id.toString())
                    data.id.let {
                        val id = it?.let { it1 -> DataNews(it1) }
                        val moveWithObjectIntent = Intent(itemView.context, NewsDetailActivity::class.java)
                        moveWithObjectIntent.putExtra(NewsDetailActivity.EXTRA_ID, id)
                        itemView.context.startActivity(moveWithObjectIntent)
                    }

                }
            }
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(userToken,data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            NewsCardExtBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NewsGetResponseItem>() {
            override fun areItemsTheSame(
                oldItem: NewsGetResponseItem,
                newItem: NewsGetResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: NewsGetResponseItem,
                newItem: NewsGetResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
