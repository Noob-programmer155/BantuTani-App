package com.bantutani.application.data.network.responses.plants

import com.google.gson.annotations.SerializedName

data class PlantsResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
