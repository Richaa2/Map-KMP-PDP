package com.richaa2.mappdp.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "locations_info")
data class LocationInfoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val latitude: Double,
    val longitude: Double,
    val title: String,
    val description: String?,
    val imageUrl: String?,
)