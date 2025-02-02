package com.richaa2.map.kmp.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.richaa2.map.kmp.data.source.local.LocationInfoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface LocationDao {

    @Upsert
    suspend fun upsertLocation(location: LocationInfoEntity)

    @Delete
    suspend fun deleteLocation(location: LocationInfoEntity): Int

    @Query("DELETE FROM locations_info WHERE id = :id")
    suspend fun deleteLocationById(id: Long): Int

    @Query("SELECT * FROM locations_info WHERE id = :id")
    suspend fun getLocationById(id: Long): LocationInfoEntity?

    @Query("SELECT * FROM locations_info")
    fun getAllLocations(): Flow<List<LocationInfoEntity>>
}