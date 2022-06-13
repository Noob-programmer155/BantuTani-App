package com.bantutani.application.ui.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bantutani.application.databinding.ActivityDetailNewsBinding

class NewsDetailActivity : AppCompatActivity() {
    private lateinit var newsDetailBinding: ActivityDetailNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsDetailBinding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(newsDetailBinding.root)

        newsDetailBinding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }
}