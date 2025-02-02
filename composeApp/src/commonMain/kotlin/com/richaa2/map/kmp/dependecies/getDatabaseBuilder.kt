package com.richaa2.map.kmp.dependecies

import androidx.room.RoomDatabase
import com.richaa2.map.kmp.data.source.local.LocationDatabase

expect fun getDatabaseBuilder(): RoomDatabase.Builder<LocationDatabase>