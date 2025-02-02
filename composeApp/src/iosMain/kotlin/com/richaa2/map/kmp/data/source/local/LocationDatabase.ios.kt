package com.richaa2.map.kmp.data.source.local

import DbClient
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask


//
//actual object LocationDatabaseConstructor : RoomDatabaseConstructor<LocationDatabase> {
//
//
//    @OptIn(ExperimentalForeignApi::class)
//    private fun documentDirectory(): String {
//        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
//            directory = NSDocumentDirectory,
//            inDomain = NSUserDomainMask,
//            appropriateForURL = null,
//            create = false,
//            error = null,
//        )
//        return requireNotNull(documentDirectory?.path)
//    }
//
//    actual override fun initialize(): LocationDatabase {
//        val dbFilePath = documentDirectory() + "/my_room.db"
//        return Room.databaseBuilder<LocationDatabase>(
//            name = dbFilePath,
//            factory =  { LocationDatabase::class.instantiateImpl() }
//        )
//    }
//
//}