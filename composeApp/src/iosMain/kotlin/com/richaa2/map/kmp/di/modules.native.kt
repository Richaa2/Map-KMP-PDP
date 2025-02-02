package com.richaa2.map.kmp.di

//import com.richaa2.map.kmp.dependecies.getDatabaseBuilder
import DbClient
import androidx.room.RoomDatabase
import com.richaa2.map.kmp.data.source.local.LocationDatabase
import com.richaa2.map.kmp.dependecies.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule: Module = module {
    singleOf(::DbClient)
    single <RoomDatabase.Builder<LocationDatabase>>{
        getDatabaseBuilder(get())
    }
//    viewModelOf(::TestViewModel)
}