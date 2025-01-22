package com.richaa2.map.kmp.di

import com.richaa2.map.kmp.data.source.local.LocationDatabase
import com.richaa2.mappdp.core.common.ErrorHandler
import com.richaa2.mappdp.data.error.DefaultErrorHandler
import com.richaa2.mappdp.data.mapper.LocationMapper
import com.richaa2.mappdp.data.repository.LocationRepositoryImpl
import com.richaa2.mappdp.domain.repository.LocationRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {

    single { get<LocationDatabase>().locationDao() }
    singleOf(::LocationRepositoryImpl).bind<LocationRepository>()
    singleOf(::LocationMapper)
    singleOf(::DefaultErrorHandler).bind<ErrorHandler>()
}