package com.richaa2.map.kmp

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.richaa2.map.kmp.presentation.map.MapViewModel
import com.richaa2.map.kmp.presentation.map.MapScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() {
    MaterialTheme {

        val mapViewModel = koinViewModel<MapViewModel>()

        MapScreen(
//            viewModel = mapViewModel,
            onAddLocation = { latLong ->
                println("onAddLocation LatLong: $latLong")
            },
            onLocationDetails = { locationInfo ->
                println("onLocationDetails LocationInfo: $locationInfo")
            }
        )
    }
}

class TestViewModel(): ViewModel() {

}