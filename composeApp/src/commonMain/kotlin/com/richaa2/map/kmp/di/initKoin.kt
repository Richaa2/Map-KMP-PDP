package com.richaa2.map.kmp.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration = {}) {
    startKoin {
        config(this)
        modules(
            sharedModule, platformModule,
            repositoryModule,
            useCaseModule,
            viewModelModule
        )
    }
}