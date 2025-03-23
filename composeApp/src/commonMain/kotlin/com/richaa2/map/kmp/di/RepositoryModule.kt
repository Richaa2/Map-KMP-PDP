package com.richaa2.map.kmp.di

import com.richaa2.map.kmp.core.LocationManager
import com.richaa2.map.kmp.data.source.local.LocationDataSource
import com.richaa2.map.kmp.core.common.ErrorHandler
import com.richaa2.map.kmp.data.error.DefaultErrorHandler
import com.richaa2.map.kmp.data.repository.LocationRepositoryImpl
import com.richaa2.map.kmp.domain.repository.LocationRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::LocationRepositoryImpl).bind<LocationRepository>()
    singleOf(::DefaultErrorHandler).bind<ErrorHandler>()
    singleOf(::LocationDataSource)
    singleOf(::LocationManager)
}