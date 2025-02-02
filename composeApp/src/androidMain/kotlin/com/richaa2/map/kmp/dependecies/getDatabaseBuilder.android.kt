package com.richaa2.map.kmp.dependecies

import DbClient
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.richaa2.map.kmp.data.source.local.LocationDatabase

actual fun getDatabaseBuilder(dbClient: DbClient): RoomDatabase.Builder<LocationDatabase> {
    val appContext = dbClient.context
    val dbFile = appContext.getDatabasePath("my_room.db")
    return Room.databaseBuilder<LocationDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}