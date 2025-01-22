package com.richaa2.map.kmp.dependecies

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.richaa2.map.kmp.data.source.local.LocationDatabase
import kotlinx.coroutines.Dispatchers


fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<LocationDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("my_room.db")
    return Room.databaseBuilder<LocationDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    ).setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
}
