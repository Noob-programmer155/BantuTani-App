package com.bantutani.application.data.local.db

import androidx.room.Database
import com.bantutani.application.data.local.entity.Commodity

@Database(entities = [Commodity::class], version = 1, exportSchema = false)
abstract class CommodityDb {

}