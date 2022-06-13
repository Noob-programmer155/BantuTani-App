package com.bantutani.application.data.network.paging.commodity

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bantutani.application.api.APIConfig
import com.bantutani.application.data.network.responses.commodity.CommodityGetAllResponseItem

class CommodityDataAllSource: PagingSource<Int, CommodityGetAllResponseItem>() {
    override fun getRefreshKey(state: PagingState<Int, CommodityGetAllResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommodityGetAllResponseItem> {
        return try {
            val page = params.key ?: CommodityDataAllSource.INITIAL_PAGE_INDEX
            val responseData = APIConfig.getApiService().comodityGetAll(page, params.loadSize)
            Log.e("data", responseData.toString())
            LoadResult.Page(
                data = responseData.commodityGetAllResponse,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.commodityGetAllResponse.isNullOrEmpty()) null else page + 1
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