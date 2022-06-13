package com.bantutani.application.data.repository

import android.annotation.SuppressLint
import com.bantutani.application.api.APIConfig2
import com.bantutani.application.data.network.responses.predict.PredictResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PredictRepository {

    fun getPredict(file : MultipartBody.Part, responsedata: (PredictResponse?) -> Unit) {
        val client = APIConfig2.getApiService().predict(file)
        client.enqueue(object : Callback<PredictResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<PredictResponse>,
                response: Response<PredictResponse>
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
            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                responsedata(null)
            }
        })
    }

}