package com.bantutani.application.ui.newsdetail

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bantutani.application.data.entitiy.news.DataNews
import com.bantutani.application.databinding.ActivityNewsDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders

class NewsDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsDetailBinding
    private val viewModel : NewsDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val Data = intent.getParcelableExtra<DataNews>(NewsDetailActivity.EXTRA_ID) as DataNews
        viewModel.getNewsDetail(Data.id)
        viewModel.fetchdata().observe(this, {
            binding.apply {
                val glideUrl = GlideUrl(
                    "http://104.196.207.218/api/public/news/v1/media/${it.images!![0]}",
                    LazyHeaders.Builder()
                        .addHeader("Accept", "*/*")
                        .build()
                )
                Glide.with(this@NewsDetailActivity)
                    .load(glideUrl)
                    .into(ivHeader)
                tvTitle.text = it.title
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvDescription.text = Html.fromHtml(it.description, Html.FROM_HTML_MODE_LEGACY)
                }else{
                    tvDescription.text = Html.fromHtml(it.description)
                }
            }
        })
    }
    companion object {
        const val EXTRA_ID = "extra_id"
    }
}