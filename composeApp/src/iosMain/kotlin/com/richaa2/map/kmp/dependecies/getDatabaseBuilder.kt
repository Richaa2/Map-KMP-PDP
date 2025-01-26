package com.richaa2.map.kmp.dependecies

import androidx.room.RoomDatabase
import com.richaa2.map.kmp.data.source.local.LocationDatabase
import platform.Foundation.NSHomeDirectory
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO


//fun getDatabaseBuilder(): RoomDatabase.Builder<LocationDatabase> {
//    val dbFilePath = NSHomeDirectory() + "/my_room.db"
//    return Room.databaseBuilder<LocationDatabase>(
//        name = dbFilePath,
//        factory =  { LocationDatabase::class.instantiateImpl() }
//    )
//.setDriver(BundledSQLiteDriver())
//.setQueryCoroutineContext(Dispatchers.IO)
//}
//
//fun getDatabase(): LocationDatabase {
//    return getDatabaseBuilder().build()
//}