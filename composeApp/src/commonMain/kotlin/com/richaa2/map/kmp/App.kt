package com.richaa2.map.kmp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.richaa2.map.kmp.dependecies.AddLocationViewModel
import com.richaa2.map.kmp.dependecies.MapViewModel
import com.richaa2.map.kmp.presentation.map.MapScreen
import com.richaa2.mappdp.presentation.locationDetails.LocationDetailsViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import mapkmp.composeapp.generated.resources.Res
import mapkmp.composeapp.generated.resources.compose_multiplatform
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() {

    MaterialTheme {
        //TODO BUILD FAILED WITH ROOM
//        val mapViewModel = koinViewModel<MapViewModel>()
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