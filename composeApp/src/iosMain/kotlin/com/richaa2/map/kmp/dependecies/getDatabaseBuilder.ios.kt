package com.richaa2.map.kmp.dependecies

import DbClient
import androidx.room.Room
import androidx.room.RoomDatabase
import com.richaa2.map.kmp.data.source.local.LocationDatabase
import com.richaa2.map.kmp.data.source.local.LocationDatabase_Impl
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask



actual fun getDatabaseBuilder(dbClient: DbClient): RoomDatabase.Builder<LocationDatabase> {
    val dbFilePath = documentDirectory() + "/my_room.db"
    return Room.databaseBuilder<LocationDatabase>(
        name = dbFilePath,
//        factory =  { }
        factory =  { LocationDatabase_Impl() }
//        factory =  { LocationDatabase::class.instantiateImpl() }
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
