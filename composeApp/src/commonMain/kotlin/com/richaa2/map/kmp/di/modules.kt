package com.richaa2.map.kmp.di

import com.richaa2.map.kmp.dependecies.MyRepository
import com.richaa2.map.kmp.dependecies.MyRepositoryImpl
import com.richaa2.map.kmp.dependecies.MyViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    singleOf(::MyRepositoryImpl).bind<MyRepository>()
    viewModelOf(::MyViewModel)
}