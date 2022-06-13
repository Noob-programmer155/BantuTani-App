package com.bantutani.application.ui.kamus

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bantutani.application.data.network.responses.plants.GetAllPlantsResponseItem
import com.bantutani.application.data.repository.PlantsRepository
import com.bantutani.application.ui.harga.HargaViewModel
import kotlinx.coroutines.flow.Flow

class KamusViewModel (context: Context): ViewModel() {
    private val repo = PlantsRepository()
    val kamusData: Flow<PagingData<GetAllPlantsResponseItem>> = repo.plantsGetAll().cachedIn(viewModelScope)
}

class KamusFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KamusViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HargaViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}