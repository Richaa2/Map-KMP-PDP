package com.richaa2.map.kmp.presentation

import androidx.compose.runtime.Composable
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MyMapScreen() {
    val cameraPositionState = rememberCameraPositionState {
        position = com.google.maps.android.compose.CameraPositionState(
            CameraPosition(
                 LatLng(37.4221, -122.0841),
                 15f,
                 0f,
                 0f
            )
        ).position
    }

    GoogleMap(
        cameraPositionState = cameraPositionState,
        onMapLoaded = {
            // Do something when the map is loaded
        }

    ) {
        val markerState = rememberMarkerState(
            position = LatLng(37.4221, -122.0841),
        )
        Marker(

            state = markerState,
            title = "Googleplex",
            snippet = "Google's Headquarters"
        )
    }
}