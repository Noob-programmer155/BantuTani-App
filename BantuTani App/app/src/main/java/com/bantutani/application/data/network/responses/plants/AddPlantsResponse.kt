package com.bantutani.application.data.network.responses.plants

import com.google.gson.annotations.SerializedName

data class AddPlantsResponse(

	@field:SerializedName("image")
	val image: List<String?>? = null,

	@field:SerializedName("regionCost")
	val regionCost: Int? = null,

	@field:SerializedName("otherNames")
	val otherNames: List<String?>? = null,

	@field:SerializedName("plantTypeImpl")
	val plantTypeImpl: String? = null,

	@field:SerializedName("stableCost")
	val stableCost: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("maxCost")
	val maxCost: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("shortDescription")
	val shortDescription: String? = null,

	@field:SerializedName("dateUpdateCost")
	val dateUpdateCost: String? = null,

	@field:SerializedName("minCost")
	val minCost: Int? = null,

	@field:SerializedName("characteristic")
	val characteristic: String? = null
)
