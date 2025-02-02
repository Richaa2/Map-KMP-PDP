package com.richaa2.map.kmp.data.source.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO


@Database(entities = [LocationInfoEntity::class], version = 1)
@ConstructedBy(LocationDatabaseConstructor::class)
abstract class LocationDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object LocationDatabaseConstructor : RoomDatabaseConstructor<LocationDatabase> {
    override fun initialize(): LocationDatabase
}