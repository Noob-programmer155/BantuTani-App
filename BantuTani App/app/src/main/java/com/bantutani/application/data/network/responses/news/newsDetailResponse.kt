package com.bantutani.application.data.network.responses.news

import com.google.gson.annotations.SerializedName

data class NewsDetailResponse(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("descriptionSummary")
	val descriptionSummary: String? = null,

	@field:SerializedName("images")
	val images: List<String?>? = null,

	@field:SerializedName("keywords")
	val keywords: List<String?>? = null,

	@field:SerializedName("dateUpdate")
	val dateUpdate: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("video")
	val video: Any? = null,

	@field:SerializedName("title")
	val title: String? = null
)
