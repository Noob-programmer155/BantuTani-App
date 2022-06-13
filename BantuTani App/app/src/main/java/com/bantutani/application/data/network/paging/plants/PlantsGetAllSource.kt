package com.bantutani.application.data.network.paging.plants

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bantutani.application.api.APIConfig
import com.bantutani.application.data.network.responses.plants.GetAllPlantsResponseItem

class PlantsGetAllSource : PagingSource<Int, GetAllPlantsResponseItem>() {
    override fun getRefreshKey(state: PagingState<Int, GetAllPlantsResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetAllPlantsResponseItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = APIConfig.getApiService().plantsGetAll(page, params.loadSize)
            Log.e("data", responseData.toString())
            LoadResult.Page(
                data = responseData.getAllPlantsResponse,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.getAllPlantsResponse.isNullOrEmpty()) null else page + 1
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