package com.bantutani.application.ui.news

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bantutani.application.data.network.responses.news.NewsGetResponseItem
import com.bantutani.application.data.preference.SessionPref
import com.bantutani.application.data.repository.NewsRepository
import com.bantutani.application.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class NewsViewModel(context: Context): ViewModel() {
    private val repo = NewsRepository()
    private val userRepo = UserRepository(SessionPref(context))

    val newsData: Flow<PagingData<NewsGetResponseItem>> = repo.newsGetAll(getToken()!!).cachedIn(viewModelScope)

    fun getToken(): String? {
        return userRepo.getToken()
    }
}

class NewsFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}