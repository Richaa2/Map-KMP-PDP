package com.richaa2.map.kmp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.richaa2.mappdp.data.source.local.LocationDao
import com.richaa2.mappdp.data.source.local.LocationInfoEntity


@Database(entities = [LocationInfoEntity::class], version = 1)
abstract class LocationDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}