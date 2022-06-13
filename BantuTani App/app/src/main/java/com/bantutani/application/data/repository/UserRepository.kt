package com.bantutani.application.data.repository

import android.annotation.SuppressLint
import com.bantutani.application.api.APIConfig
import com.bantutani.application.data.entitiy.user.DataLogin
import com.bantutani.application.data.network.responses.publicapi.LoginResponse
import com.bantutani.application.data.network.responses.publicapi.RegisterResponse
import com.bantutani.application.data.network.responses.user.UserResponse
import com.bantutani.application.data.preference.SessionPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(session: SessionPref) {
    private val sessionservice = session
    fun userSignIn(username: String, password: String, returndata: (DataLogin?, Error?) -> Unit) {
        val client = APIConfig.getApiService()
            .userSignIn(username, password)
        client.enqueue(object : Callback<LoginResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    val responseHeader = response.headers()
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val token = responseHeader.get("Authorization")
                        val data = token?.let {
                            DataLogin(
                                responseBody.image,
                                responseBody.id,
                                responseBody.email,
                                responseBody.username,
                                responseBody.fullName,
                                responseBody.status,
                                it
                            )
                        }
                        returndata(data, null)
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
            }
        })
    }
    fun userSignUp(fullName: String, username: String, password: String, email: String, image: String, status: String, responsedata: (Boolean, String) -> Unit){
        val client = APIConfig.getApiService()
            .userSignUp(fullName, username, password, email, image, status)
        client.enqueue(object : Callback<RegisterResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        responsedata(false, "Pendaftaran Berhasil")
                    }
                } else {
                    responsedata(true, "Pendaftaran Gagal")
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                responsedata(true, "Pendaftaran Gagal")
            }
        })
    }
    fun userModify(token: String, username: String, password: String, email: String, image: String, status: String, responsedata: (Boolean) -> Unit){
        val client = APIConfig.getApiService()
            .userModify(token, username, password, email, image, status)
        client.enqueue(object : Callback<String> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        responsedata(false)
                    }
                } else {
                    responsedata(true)
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                responsedata(true)
            }
        })
    }
    fun userDelete(token: String, id: Int, responsedata: (Boolean) -> Unit){
        val client = APIConfig.getApiService()
            .userDelete(token, id)
        client.enqueue(object : Callback<UserResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
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
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                responsedata(true)
            }
        })
    }
    fun userPasswordModify(token: String, id: Int, oldPassword: String, newPassword: String, responsedata: (Boolean) -> Unit){
        val client = APIConfig.getApiService()
            .userPasswordModify(token, id, oldPassword, newPassword)
        client.enqueue(object : Callback<UserResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
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
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                responsedata(true)
            }
        })
    }
    fun userSaveSession(id: Int,fullName: String,  nama:String,email: String, image: String, status: String, token: String){
        sessionservice.setSessionData(id, fullName, nama, email, image, status, token)
    }
    fun userGetSession(responsedata: (DataLogin) -> Unit){
        sessionservice.let {
            val user = DataLogin(
                it.getUserImage()!!,
                it.getUserId()!!,
                it.getUserEmail()!!,
                it.getUserName()!!,
                it.getFullName()!!,
                it.getUserStatus()!!,
                it.getUserToken()!!
            )
            responsedata(user)
        }
    }
    fun getToken() : String? {
        return sessionservice.getUserToken()
    }
}