package com.bantutani.application.data.network

import com.bantutani.application.data.network.responses.commodity.CommodityGetAllResponse
import com.bantutani.application.data.network.responses.news.NewsDetailResponse
import com.bantutani.application.data.network.responses.news.NewsGetResponse
import com.bantutani.application.data.network.responses.plants.GetAllPlantsResponse
import com.bantutani.application.data.network.responses.plants.PlantsResponse
import com.bantutani.application.data.network.responses.publicapi.LoginResponse
import com.bantutani.application.data.network.responses.publicapi.RegisterResponse
import com.bantutani.application.data.network.responses.user.UserResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface APIService {

    //    User Public
    @FormUrlEncoded
    @POST("public/user/v1/signup")
    fun userSignUp(
        @Field("fullName") fullName: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("image") image: String,
        @Field("status") status: String
    ): Call<RegisterResponse>

    @POST("public/user/v1/login")
    fun userSignIn(
        @Query("username") email: String,
        @Query("password") password: String
    ): Call<LoginResponse>

    @GET("public/user/v1/avatar/get/all")
    fun userAvatarGetAll(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<ArrayList<String>>

    //    User Private
    @FormUrlEncoded
    @PUT("user/v1/modify")
    fun userModify(
        @Header("Authorization") token: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("image") image: String,
        @Field("status") status: String
    ): Call<String>

    @DELETE("user/v1/delete")
    fun userDelete(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): Call<UserResponse>

    @PUT("user/v1/password/modify")
    fun userPasswordModify(
        @Header("Authorization") token: String,
        @Query("id") id: Int,
        @Query("oldPassword") oldPassword: String,
        @Query("newPassword") newPassword: String
    ): Call<UserResponse>

    @FormUrlEncoded
    @POST("user/v1/avatar/add")
    fun userAvatarAdd(
        @Field("avatar") avatar: String
    ): Call<String>

    @DELETE("user/v1/avatar/delete")
    fun userAvatarDelete(
        @Path("name") name: String
    ): Call<UserResponse>


    //    Plants/v1 Public

    @GET("plants/v1/data/search/get")
    fun plantsSearch(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetAllPlantsResponse

    @GET("plants/v1/data/all")
    fun plantsGetAll(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): GetAllPlantsResponse

    //    Plants/v1 Private
    @FormUrlEncoded
    @POST("plants/v1/data/add")
    fun plantsAdd(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("otherNames") otherNames: List<String>,
        @Field("plantTypeImpl") plantTypeImpl: String,
        @Field("image") image: List<String>,
        @Field("shortDescription") shortDescription: String,
        @Field("characteristic") characteristic: String,
        @Field("stableCost") stableCost: Int,
        @Field("maxCost") maxCost: Int,
        @Field("minCost") minCost: Int,
        @Field("regionCost") regionCost: Int,
        @Field("dateUpdateCost") dateUpdateCost: String,
    ): Call<PlantsResponse>

    @PUT("plants/v1/data/modify")
    fun plantsModify(
        @Header("Authorization") token: String,
        @Field("id") id: Int,
        @Field("name") name: String,
        @Field("otherNames") otherNames: List<String>,
        @Field("plantTypeImpl") plantTypeImpl: String,
        @Field("image") image: List<String>,
        @Field("shortDescription") shortDescription: String,
        @Field("characteristic") characteristic: String,
        @Field("stableCost") stableCost: Int,
        @Field("maxCost") maxCost: Int,
        @Field("minCost") minCost: Int,
        @Field("regionCost") regionCost: Int,
        @Field("dateUpdateCost") dateUpdateCost: String,
    ): Call<PlantsResponse>

    @PUT("plants/v1/data/cost/modify")
    fun plantsCostModify(
        @Header("Authorization") token: String,
        @Query("plantId") plantId: Int,
        @Query("regionCost") regionCost: Int,
        @Query("stableCost") stableCost: Int,
        @Query("maxCost") maxCost: Int,
    ): Call<PlantsResponse>

    @DELETE("plants/v1/data/delete")
    fun plantsDelete(
        @Header("Authorization") token: String,
        @Query("id") id: Int,
    ): Call<PlantsResponse>

    // Plants/tipsntrick private
    @PUT("plants/v1/tipsntrick/data/modify")
    fun plantsTipsModify(
        @Header("Authorization") token: String,
        @Field("id") id: Int,
        @Field("name") name: String,
        @Field("description") description: String,
        @Field("animation") animation: String,
        @Field("typeStep") typeStep: String,
        @Field("typeActivity") typeActivity: String,
        @Field("authorTipsNTrick") authorTipsNTrick: Int,
        @Field("plantsCareTips") plantsCareTips: Int,
        @Field("plantsPlantingTips") plantsPlantingTips: Int,
        ): Call<PlantsResponse>

    @DELETE("plants/v1/tipsntrick/data/delete")
    fun plantsTipsDelete(
        @Header("Authorization") token: String,
        @Field("id") id: Int,
    ): Call<PlantsResponse>

//  /News/Public
    @GET("public/news/v1/data/get")
    fun newsGet(
        @Query("id") id: Int
    ): Call<NewsDetailResponse>

    @GET("public/news/v1/data/2021-12-21/2022-12-25/get")
    suspend fun newsGetAll(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): NewsGetResponse

//  /Comodity/Public
    @GET("public/commodity/v1/data/all")
    suspend fun comodityGetAll(
        @Query("page") page:Int,
        @Query("size") size: Int
    ): CommodityGetAllResponse

//    image
    @GET("public/news/v1/media/{name}")
    fun retrieveImageData(
        @Path("name") name: String
    ): Call<ResponseBody>
}