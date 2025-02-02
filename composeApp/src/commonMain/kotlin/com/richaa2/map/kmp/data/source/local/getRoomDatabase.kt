package com.richaa2.map.kmp.data.source.local

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

fun getRoomDatabase(
    builder: RoomDatabase.Builder<LocationDatabase>
): LocationDatabase {
    return builder
//        .addMigrations(MIGRATIONS)
//        .fallbackToDestructiveMigrationOnDowngrade()
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}