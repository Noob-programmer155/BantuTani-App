package com.bantutani.application.data.network.responses.news

import com.google.gson.annotations.SerializedName

data class DataItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("dateUpdate")
	val dateUpdate: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("video")
	val video: Any? = null,

	@field:SerializedName("title")
	val title: String? = null
)

data class NewsGetResponseAll(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null
)
