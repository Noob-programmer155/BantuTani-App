package com.bantutani.application.ui.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bantutani.application.data.network.responses.predict.PredictResponse
import com.bantutani.application.data.repository.PredictRepository
import okhttp3.MultipartBody

class CameraViewModel : ViewModel() {
    private val repo = PredictRepository()
    private val _data = MutableLiveData<PredictResponse>()
    val data: LiveData<PredictResponse> get() = _data

    fun sendImage(file : MultipartBody.Part){
        repo.getPredict(file){
            _data.postValue(it)
        }
    }
}