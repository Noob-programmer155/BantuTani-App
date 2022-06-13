package com.bantutani.application.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bantutani.application.data.network.paging.commodity.CommodityDataAllSource
import com.bantutani.application.data.network.responses.commodity.CommodityGetAllResponseItem
import kotlinx.coroutines.flow.Flow

class CommodityRepository {
    fun commodityGetAll(): Flow<PagingData<CommodityGetAllResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1
            ),
            pagingSourceFactory = {
                CommodityDataAllSource()
            }
        ).flow
    }
}