package com.bantutani.application.ui.newsdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bantutani.application.data.network.responses.news.NewsDetailResponse
import com.bantutani.application.data.repository.NewsRepository

class NewsDetailViewModel : ViewModel() {
    val repo = NewsRepository()
    private val _data = MutableLiveData<NewsDetailResponse>()
    val data: LiveData<NewsDetailResponse> get() = _data

    fun getNewsDetail(id: Int){
        repo.getNews(id){
            if(it != null){
                _data.postValue(it)
            }
        }
    }
    fun fetchdata() = data
}