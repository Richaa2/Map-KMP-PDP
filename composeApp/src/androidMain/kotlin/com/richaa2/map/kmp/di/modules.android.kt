package com.richaa2.map.kmp.di

import com.richaa2.map.kmp.core.LocationManager
import com.richaa2.map.kmp.data.source.remote.googleMapApiClient
import com.richaa2.map.kmp.platform.DbClient
import com.richaa2.map.kmp.data.source.local.DriverFactory
import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.PermissionsController
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule: Module = module {
    singleOf(::DbClient)
    singleOf(::DriverFactory)
    single {
        LocationTracker(
            permissionsController = PermissionsController(applicationContext = androidApplication())
        )
    }
    singleOf(::LocationManager)
    singleOf(::googleMapApiClient)
}