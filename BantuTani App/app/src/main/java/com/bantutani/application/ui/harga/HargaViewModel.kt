package com.bantutani.application.ui.harga

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bantutani.application.data.network.responses.commodity.CommodityGetAllResponseItem
import com.bantutani.application.data.repository.CommodityRepository
import kotlinx.coroutines.flow.Flow

class HargaViewModel(context: Context): ViewModel() {
    private val repo = CommodityRepository()
    val hargaData: Flow<PagingData<CommodityGetAllResponseItem>> = repo.commodityGetAll().cachedIn(viewModelScope)
}

class HargaFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HargaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HargaViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

