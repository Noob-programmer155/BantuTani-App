package com.bantutani.application.data.repository

import android.annotation.SuppressLint
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bantutani.application.api.APIConfig
import com.bantutani.application.data.network.paging.PlantsSearchSource
import com.bantutani.application.data.network.paging.plants.PlantsGetAllSource
import com.bantutani.application.data.network.responses.plants.GetAllPlantsResponseItem
import com.bantutani.application.data.network.responses.plants.PlantsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlantsRepository {

    fun plantsSearch(q: String): Flow<PagingData<GetAllPlantsResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1
            ),
            pagingSourceFactory = {
                PlantsSearchSource(q)
            }
        ).flow
    }

    fun plantsGetAll(): Flow<PagingData<GetAllPlantsResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1
            ),
            pagingSourceFactory = {
                PlantsGetAllSource()
            }
        ).flow
    }


    fun plantsAdd(
        token: String,
        name: String,
        otherNames: List<String>,
        plantTypeImpl: String,
        image: List<String>,
        shortDescription: String,
        characteristic: String,
        stableCost: Int,
        maxCost: Int,
        minCost: Int,
        regionCost: Int,
        dateUpdateCost: String,
        responsedata: (Boolean) -> Unit
    ) {
        val client = APIConfig.getApiService()
            .plantsAdd(
                token,
                name,
                otherNames,
                plantTypeImpl,
                image,
                shortDescription,
                characteristic,
                stableCost,
                maxCost,
                minCost,
                regionCost,
                dateUpdateCost
            )
        client.enqueue(object : Callback<PlantsResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<PlantsResponse>,
                response: Response<PlantsResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        responsedata(true)
                    }
                } else {
                    responsedata(true)
                }
            }

            override fun onFailure(call: Call<PlantsResponse>, t: Throwable) {
                responsedata(true)
            }
        })
    }

    fun plantsModify(
        token: String,
        id: Int,
        name: String,
        otherNames: List<String>,
        plantTypeImpl: String,
        image: List<String>,
        shortDescription: String,
        characteristic: String,
        stableCost: Int,
        maxCost: Int,
        minCost: Int,
        regionCost: Int,
        dateUpdateCost: String,
        responsedata: (Boolean) -> Unit
    ) {
        val client = APIConfig.getApiService()
            .plantsModify(
                token,
                id,
                name,
                otherNames,
                plantTypeImpl,
                image,
                shortDescription,
                characteristic,
                stableCost,
                maxCost,
                minCost,
                regionCost,
                dateUpdateCost
            )
        client.enqueue(object : Callback<PlantsResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<PlantsResponse>,
                response: Response<PlantsResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        responsedata(true)
                    }
                } else {
                    responsedata(true)
                }
            }

            override fun onFailure(call: Call<PlantsResponse>, t: Throwable) {
                responsedata(true)
            }
        })
    }

    fun plantsCostModify(token: String, plantId: Int, regionCost: Int, stableCost: Int, maxCost: Int, responsedata: (Boolean) -> Unit){
        val client = APIConfig.getApiService()
            .plantsCostModify(token, plantId, regionCost, stableCost, maxCost)
        client.enqueue(object : Callback<PlantsResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<PlantsResponse>,
                response: Response<PlantsResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        responsedata(true)
                    }
                } else {
                    responsedata(true)
                }
            }
            override fun onFailure(call: Call<PlantsResponse>, t: Throwable) {
                responsedata(true)
            }
        })
    }

    fun plantsCostModify(token: String, id: Int, responsedata: (Boolean) -> Unit){
        val client = APIConfig.getApiService()
            .plantsDelete(token, id)
        client.enqueue(object : Callback<PlantsResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<PlantsResponse>,
                response: Response<PlantsResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        responsedata(true)
                    }
                } else {
                    responsedata(true)
                }
            }
            override fun onFailure(call: Call<PlantsResponse>, t: Throwable) {
                responsedata(true)
            }
        })
    }

    fun plantsTipsModify(token: String,id: Int, name: String, description: String, animation: String, typeStep: String, typeActivity: String, authorTipsNTrick: Int, plantsCareTips: Int, plantsPlantingTips: Int, responsedata: (Boolean) -> Unit){
        val client = APIConfig.getApiService()
            .plantsTipsModify(token, id, name, description, animation, typeStep, typeActivity, authorTipsNTrick, plantsCareTips, plantsPlantingTips)
        client.enqueue(object : Callback<PlantsResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<PlantsResponse>,
                response: Response<PlantsResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        responsedata(true)
                    }
                } else {
                    responsedata(true)
                }
            }
            override fun onFailure(call: Call<PlantsResponse>, t: Throwable) {
                responsedata(true)
            }
        })
    }

    fun plantsTipsDelete(token: String, id: Int,responsedata: (Boolean) -> Unit){
        val client = APIConfig.getApiService()
            .plantsTipsDelete(token, id)
        client.enqueue(object : Callback<PlantsResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<PlantsResponse>,
                response: Response<PlantsResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        responsedata(true)
                    }
                } else {
                    responsedata(true)
                }
            }
            override fun onFailure(call: Call<PlantsResponse>, t: Throwable) {
                responsedata(true)
            }
        })
    }
}