package com.richaa2.map.kmp.di

import com.richaa2.map.kmp.data.source.local.LocationDatabase
import com.richaa2.map.kmp.data.source.local.getRoomDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single<LocationDatabase> {
        getRoomDatabase(get())
    }
}