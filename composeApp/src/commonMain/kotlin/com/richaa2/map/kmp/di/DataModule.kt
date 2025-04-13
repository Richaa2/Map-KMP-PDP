package com.richaa2.map.kmp.di

import com.richaa2.db.AppDatabase
import com.richaa2.map.kmp.data.source.local.driver.createDatabase
import com.richaa2.map.kmp.data.source.remote.RouteNetworkSource
import com.richaa2.map.kmp.data.source.remote.client.ktorGoogleMapApiClient
import org.koin.dsl.module

val dataModule = module {
    single<AppDatabase> { createDatabase(get())  }
    single { RouteNetworkSource(client = ktorGoogleMapApiClient) }

}