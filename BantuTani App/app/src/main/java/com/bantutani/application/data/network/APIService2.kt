package com.bantutani.application.data.network

import com.bantutani.application.data.network.responses.predict.PredictResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface APIService2 {

    @Multipart
    @POST("pred")
    fun predict(
        @Part file: MultipartBody.Part,
    ): Call<PredictResponse>
}