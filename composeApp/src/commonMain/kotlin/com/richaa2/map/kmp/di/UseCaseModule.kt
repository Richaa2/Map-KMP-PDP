package com.richaa2.map.kmp.di

import com.richaa2.map.kmp.domain.usecase.cameraPosition.GetCameraPositionUseCase
import com.richaa2.map.kmp.domain.usecase.cameraPosition.SaveCameraPositionUseCase
import com.richaa2.map.kmp.domain.usecase.locationInfo.DeleteLocationInfoUseCase
import com.richaa2.map.kmp.domain.usecase.userLocation.GetCurrentLocationUseCase
import com.richaa2.map.kmp.domain.usecase.locationInfo.GetLocationInfoUseCase
import com.richaa2.map.kmp.domain.usecase.route.GetRoutesUseCase
import com.richaa2.map.kmp.domain.usecase.locationInfo.GetSavedLocationsInfoUseCase
import com.richaa2.map.kmp.domain.usecase.locationInfo.SaveLocationInfoUseCase
import com.richaa2.map.kmp.domain.usecase.userLocation.StartLocationUpdatesUseCase
import com.richaa2.map.kmp.domain.usecase.userLocation.StopLocationUpdatesUseCase
import com.richaa2.map.kmp.domain.usecase.locationInfo.UpdateLocationInfoUseCase
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
    singleOf(::SaveCameraPositionUseCase)
    singleOf(::GetCameraPositionUseCase)
}