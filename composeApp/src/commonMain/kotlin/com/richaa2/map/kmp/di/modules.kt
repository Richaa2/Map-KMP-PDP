package com.richaa2.map.kmp.di

import com.richaa2.db.AppDatabase
import com.richaa2.map.kmp.dependecies.createDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single<AppDatabase> { createDatabase(get())  }

}