package com.bantutani.application.data.network.responses.commodity

import com.google.gson.annotations.SerializedName

data class CommodityGetAllResponse(

	@field:SerializedName("data")
	val commodityGetAllResponse: List<CommodityGetAllResponseItem>
)

data class CommodityGetAllResponseItem(

	@field:SerializedName("currentCost")
	val currentCost: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("costIncrease")
	val costIncrease: Boolean? = null,

	@field:SerializedName("previousCost")
	val previousCost: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
