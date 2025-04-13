package com.richaa2.map.kmp.di

import com.richaa2.map.kmp.core.error.ErrorHandler
import com.richaa2.map.kmp.core.error.impl.DefaultErrorHandlerImpl
import com.richaa2.map.kmp.data.repository.LocationRepositoryImpl
import com.richaa2.map.kmp.data.repository.RoutesRepositoryImpl
import com.richaa2.map.kmp.data.source.local.LocationDataSourceImpl
import com.richaa2.map.kmp.domain.repository.LocationRepository
import com.richaa2.map.kmp.domain.repository.RoutesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::LocationRepositoryImpl).bind<LocationRepository>()
    singleOf(::RoutesRepositoryImpl).bind<RoutesRepository>()
    singleOf(::DefaultErrorHandlerImpl).bind<ErrorHandler>()
    singleOf(::LocationDataSourceImpl)
}