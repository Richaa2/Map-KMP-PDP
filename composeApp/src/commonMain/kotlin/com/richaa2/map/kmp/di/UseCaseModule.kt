package com.richaa2.map.kmp.di

import com.richaa2.mappdp.domain.usecase.DeleteLocationInfoUseCase
import com.richaa2.mappdp.domain.usecase.GetLocationInfoUseCase
import com.richaa2.mappdp.domain.usecase.GetSavedLocationsInfoUseCase
import com.richaa2.mappdp.domain.usecase.SaveLocationInfoUseCase
import com.richaa2.mappdp.domain.usecase.UpdateLocationInfoUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::SaveLocationInfoUseCase)
    singleOf(::GetSavedLocationsInfoUseCase)
    singleOf(::DeleteLocationInfoUseCase)
    singleOf(::UpdateLocationInfoUseCase)
    singleOf(::GetLocationInfoUseCase)
}