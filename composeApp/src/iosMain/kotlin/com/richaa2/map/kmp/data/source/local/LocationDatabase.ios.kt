package com.richaa2.map.kmp.data.source.local


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