package com.bantutani.application.data.repository

import android.annotation.SuppressLint
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bantutani.application.api.APIConfig
import com.bantutani.application.data.network.paging.news.NewsAllSource
import com.bantutani.application.data.network.responses.news.NewsDetailResponse
import com.bantutani.application.data.network.responses.news.NewsGetResponseItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {

    fun getNews(id: Int, responsedata: (NewsDetailResponse?) -> Unit){
        val client = APIConfig.getApiService().newsGet(id)
        client.enqueue(object : Callback<NewsDetailResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<NewsDetailResponse>,
                response: Response<NewsDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        responsedata(responseBody)
                    }
                } else {
                    responsedata(null)
                }
            }
            override fun onFailure(call: Call<NewsDetailResponse>, t: Throwable) {
                responsedata(null)
            }
        })
    }

    fun newsGetAll(token: String): Flow<PagingData<NewsGetResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1
            ),
            pagingSourceFactory = {
                NewsAllSource(token)
            }
        ).flow
    }
}