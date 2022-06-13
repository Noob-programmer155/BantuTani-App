package com.bantutani.application.data.entitiy.predict

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataPredict(
    val image: List<String?>? = null,

    val otherNames: List<String?>? = null,

    val attributePlantsType: String? = null,

    val dateUpdate: String? = null,

    val name: String? = null,

    val description: String? = null,

    val id: Int? = null,

    val authorPlantsAttribute: String? = null,

    val plantsCares: List<String?>? = null
): Parcelable
