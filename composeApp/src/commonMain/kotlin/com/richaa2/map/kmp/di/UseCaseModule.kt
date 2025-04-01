package com.richaa2.map.kmp.di

import com.richaa2.map.kmp.domain.usecase.DeleteLocationInfoUseCase
import com.richaa2.map.kmp.domain.usecase.GetCurrentLocationUseCase
import com.richaa2.map.kmp.domain.usecase.GetLocationInfoUseCase
import com.richaa2.map.kmp.domain.usecase.GetRoutesUseCase
import com.richaa2.map.kmp.domain.usecase.GetSavedLocationsInfoUseCase
import com.richaa2.map.kmp.domain.usecase.SaveLocationInfoUseCase
import com.richaa2.map.kmp.domain.usecase.StartLocationUpdatesUseCase
import com.richaa2.map.kmp.domain.usecase.StopLocationUpdatesUseCase
import com.richaa2.map.kmp.domain.usecase.UpdateLocationInfoUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::SaveLocationInfoUseCase)
    singleOf(::GetSavedLocationsInfoUseCase)
    singleOf(::DeleteLocationInfoUseCase)
    singleOf(::UpdateLocationInfoUseCase)
    singleOf(::GetLocationInfoUseCase)
    singleOf(::GetCurrentLocationUseCase)
    singleOf(::StopLocationUpdatesUseCase)
    singleOf(::StartLocationUpdatesUseCase)
    singleOf(::GetRoutesUseCase)
}