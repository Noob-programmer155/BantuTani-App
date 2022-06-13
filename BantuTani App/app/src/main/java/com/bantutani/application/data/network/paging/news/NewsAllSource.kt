package com.bantutani.application.data.network.paging.news

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bantutani.application.api.APIConfig
import com.bantutani.application.data.network.responses.news.NewsGetResponseItem

class NewsAllSource(token: String): PagingSource<Int, NewsGetResponseItem>()  {
    private val token = token
    override fun getRefreshKey(state: PagingState<Int, NewsGetResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsGetResponseItem> {
        return try {
            val page = params.key ?: NewsAllSource.INITIAL_PAGE_INDEX
            val responseData = APIConfig.getApiService().newsGetAll(token, page, params.loadSize)
            Log.e("data", responseData.toString())
            LoadResult.Page(
                data = responseData.newsGetResponse,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.newsGetResponse.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            Log.e("error", exception.toString())
            return LoadResult.Error(exception)
        }
    }
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}