package com.bantutani.application.data.network.responses.plants

import com.google.gson.annotations.SerializedName

data class GetAllPlantsResponse(

	@field:SerializedName("GetAllPlantsResponse")
	val getAllPlantsResponse: List<GetAllPlantsResponseItem>
)

data class GetAllPlantsResponseItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("plantTypeImpl")
	val plantTypeImpl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
