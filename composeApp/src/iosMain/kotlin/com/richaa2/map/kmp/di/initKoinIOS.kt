package com.richaa2.map.kmp.di

import org.koin.dsl.KoinAppDeclaration
import org.koin.core.context.startKoin

fun initKoinIOS(config: KoinAppDeclaration = {}) {
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