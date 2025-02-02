package com.richaa2.map.kmp.dependecies

import DbClient
import androidx.room.RoomDatabase
import com.richaa2.map.kmp.data.source.local.LocationDatabase

expect fun getDatabaseBuilder(dbClient: DbClient): RoomDatabase.Builder<LocationDatabase>