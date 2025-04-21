package com.richaa2.map.kmp.di

import com.richaa2.map.kmp.presentation.app.AppViewModel
import com.richaa2.map.kmp.presentation.screens.addLocation.AddLocationViewModel
import com.richaa2.map.kmp.presentation.screens.map.MapViewModel
import com.richaa2.map.kmp.presentation.screens.locationDetails.LocationDetailsViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::LocationDetailsViewModel)
    viewModelOf(::AddLocationViewModel)
    viewModelOf(::LocationDetailsViewModel)
    viewModelOf(::MapViewModel)
    viewModelOf(::AppViewModel)
}
