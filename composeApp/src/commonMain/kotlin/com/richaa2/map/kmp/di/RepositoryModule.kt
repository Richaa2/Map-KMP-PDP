package com.richaa2.map.kmp.di

import com.richaa2.map.kmp.core.LocationManager
import com.richaa2.map.kmp.data.source.local.LocationDataSource
import com.richaa2.map.kmp.core.common.ErrorHandler
import com.richaa2.map.kmp.data.error.DefaultErrorHandler
import com.richaa2.map.kmp.data.repository.LocationRepositoryImpl
import com.richaa2.map.kmp.data.repository.RoutesRepositoryImpl
import com.richaa2.map.kmp.data.source.remote.RouteNetworkSource
import com.richaa2.map.kmp.data.source.remote.googleMapApiClient
import com.richaa2.map.kmp.domain.repository.LocationRepository
import com.richaa2.map.kmp.domain.repository.RoutesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::LocationRepositoryImpl).bind<LocationRepository>()
    singleOf(::RoutesRepositoryImpl).bind<RoutesRepository>()
    singleOf(::DefaultErrorHandler).bind<ErrorHandler>()
    singleOf(::LocationDataSource)
    single {
        RouteNetworkSource(client = googleMapApiClient)
    }
}