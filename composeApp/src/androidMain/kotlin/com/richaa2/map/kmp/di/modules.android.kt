package com.richaa2.map.kmp.di

import com.richaa2.map.kmp.dependecies.DbClient
import com.richaa2.map.kmp.dependecies.DriverFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule: Module = module {
    singleOf(::DbClient)
    singleOf(::DriverFactory)

}