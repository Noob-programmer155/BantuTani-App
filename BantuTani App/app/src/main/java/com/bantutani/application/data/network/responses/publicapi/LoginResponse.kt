package com.bantutani.application.data.network.responses.publicapi

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("fullName")
	val fullName: String,

	@field:SerializedName("status")
	val status: String
)
