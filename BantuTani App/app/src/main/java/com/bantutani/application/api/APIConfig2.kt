package com.bantutani.application.api

import com.bantutani.application.data.network.APIService2
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIConfig2 {
    companion object {
        fun getApiService(): APIService2 {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().apply {
                addInterceptor(loggingInterceptor)
            }.build()
            val retrofit = Retrofit.Builder()
                .baseUrl("http://34.139.146.154/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(APIService2::class.java)
        }
    }
}