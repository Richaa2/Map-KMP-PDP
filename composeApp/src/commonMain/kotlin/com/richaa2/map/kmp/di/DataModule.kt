package com.richaa2.map.kmp.di

import com.richaa2.db.AppDatabase
import com.richaa2.map.kmp.data.source.LocalSource
import com.richaa2.map.kmp.data.source.RemoteSource
import com.richaa2.map.kmp.data.source.local.LocalSourceImpl
import com.richaa2.map.kmp.data.source.local.driver.createDatabase
import com.richaa2.map.kmp.data.source.remote.RemoteSourceImpl
import com.richaa2.map.kmp.data.source.remote.client.ktorGoogleMapApiClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single<AppDatabase> { createDatabase(get()) }
    single { RemoteSourceImpl(client = ktorGoogleMapApiClient) }.bind<RemoteSource>()
    singleOf(::LocalSourceImpl).bind<LocalSource>()
}