package com.bantutani.application.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bantutani.application.data.network.responses.commodity.CommodityGetAllResponseItem
import com.bantutani.application.data.network.responses.news.NewsGetResponseItem
import com.bantutani.application.databinding.NewsCardBinding
import com.bantutani.application.databinding.PriceCardBinding
import com.bumptech.glide.Glide

class PriceHomeAdapter: RecyclerView.Adapter<PriceHomeAdapter.ViewHolder>() {
    private val hargaHome = ArrayList<CommodityGetAllResponseItem>()

    fun setListHargaHome(hargaHome: ArrayList<CommodityGetAllResponseItem>){
        hargaHome.clear()
        hargaHome.addAll(hargaHome)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val bindingHarga: PriceCardBinding) : RecyclerView.ViewHolder(bindingHarga.root) {
        fun bind(commodity: CommodityGetAllResponseItem) {
            bindingHarga.apply {
                Glide.with(itemView)
                    .load(commodity.icon)
                    .into(comodityImage)
                comodityName.text = commodity.name
                comodityPrice.text = commodity.currentCost.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceHomeAdapter.ViewHolder {
        val view = PriceCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PriceHomeAdapter.ViewHolder, position: Int) {
        holder.bind(hargaHome[position])
    }

    override fun getItemCount(): Int = hargaHome.size
}


//Berita Adapter
class NewsHomeAdapter: RecyclerView.Adapter<NewsHomeAdapter.ViewHolder>() {
    private val newsHome = ArrayList<NewsGetResponseItem>()

    fun setListNewsHome(newsHome: ArrayList<NewsGetResponseItem>){
        newsHome.clear()
        newsHome.addAll(newsHome)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val bindingNews: NewsCardBinding) : RecyclerView.ViewHolder(bindingNews.root) {
        fun bind(news: NewsGetResponseItem) {
            bindingNews.apply {
                Glide.with(itemView)
                    .load(news.image)
                    .into(imgNews)
                txtNews.text = news.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHomeAdapter.ViewHolder {
        val view = NewsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsHomeAdapter.ViewHolder, position: Int) {
        holder.bind(newsHome[position])
    }

    override fun getItemCount(): Int = newsHome.size
}