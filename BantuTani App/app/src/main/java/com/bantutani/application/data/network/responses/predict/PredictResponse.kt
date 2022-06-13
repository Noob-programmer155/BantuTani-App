package com.bantutani.application.data.network.responses.predict

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("image")
	val image: List<String?>? = null,

	@field:SerializedName("otherNames")
	val otherNames: List<String?>? = null,

	@field:SerializedName("attributePlantsType")
	val attributePlantsType: String? = null,

	@field:SerializedName("dateUpdate")
	val dateUpdate: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("authorPlantsAttribute")
	val authorPlantsAttribute: String? = null,

	@field:SerializedName("plantsCares")
	val plantsCares: List<Any?>? = null
)
