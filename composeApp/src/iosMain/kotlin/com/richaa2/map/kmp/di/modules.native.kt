package com.richaa2.map.kmp.di

//import com.richaa2.map.kmp.dependecies.getDatabaseBuilder
import com.richaa2.map.kmp.dependecies.DbClient
import com.richaa2.map.kmp.dependecies.DriverFactory
import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.ios.PermissionsController

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import platform.CoreLocation.kCLLocationAccuracyBest

actual val platformModule: Module = module {
    singleOf(::DbClient)
    singleOf(::DriverFactory)
    single {
        LocationTracker(
            permissionsController = PermissionsController(),
            accuracy = kCLLocationAccuracyBest
        )
    }
}