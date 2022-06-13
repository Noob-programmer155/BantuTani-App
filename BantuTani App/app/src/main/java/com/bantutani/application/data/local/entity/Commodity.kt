package com.bantutani.application.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commodity")
class Commodity(
    @field:ColumnInfo(name = "name")
    @field:PrimaryKey
    val name: String,
    @field:ColumnInfo(name = "icon")
    val icon: String,
    @field:ColumnInfo(name = "currentCost")
    val currentCost: Int,
    @field:ColumnInfo(name = "previousCost")
    val previousCost: Int,
    @field:ColumnInfo(name = "costIncrease")
    val costIncrease: Int,
)