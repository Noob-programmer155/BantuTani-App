package com.bantutani.application.ui.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bantutani.application.data.network.responses.predict.PredictResponse
import com.bantutani.application.data.repository.PredictRepository
import okhttp3.MultipartBody

class ScanViewModel : ViewModel() {
    val repo = PredictRepository()
    private val _data = MutableLiveData<PredictResponse>()
    val data: LiveData<PredictResponse> get() = _data

    fun sendData(image: MultipartBody.Part) {
        repo.getPredict(image){
            if (it != null){
                _data.postValue(it)
            }
        }
    }

    fun fetchdata() = data
}