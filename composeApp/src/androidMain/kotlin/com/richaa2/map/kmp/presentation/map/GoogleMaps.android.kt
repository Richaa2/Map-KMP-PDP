package com.richaa2.map.kmp.presentation.map

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.presentation.map.camera.CameraPositionState
import com.richaa2.map.kmp.presentation.map.clustering.LocationClusterItem
import com.richaa2.map.kmp.presentation.map.clustering.LocationClustering

@OptIn(MapsComposeExperimentalApi::class)
@SuppressLint("MissingPermission")
@Composable
actual fun GoogleMaps(
    modifier: Modifier,
    savedLocations: List<LocationInfo>,
    onMarkerClick: (LocationInfo) -> Unit,
    onMapLongClick: (LatLong) -> Unit,
    cameraPositionState: CameraPositionState,
) {

    val nativeCameraPositionState = rememberCameraPositionState()

    val clusterItems = remember(savedLocations) {
        savedLocations.map { locationInfo ->
            LocationClusterItem(locationInfo)
        }
    }
    val uiSettings = remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = false,
                myLocationButtonEnabled = false
            )
        )
    }
    GoogleMap(
        cameraPositionState = nativeCameraPositionState,
        modifier = modifier,
        uiSettings = uiSettings.value,
        onMapClick = { latlng ->

        },
        onMapLongClick = { latlng ->
            onMapLongClick(
                LatLong(
                    latlng.latitude,
                    latlng.longitude
                )
            )
        },
    ) {
        MapEffect(Unit) {
            it.isMyLocationEnabled = true
            cameraPositionState.setNativeMap(it)
        }
        LocationClustering(
            clusterItems = clusterItems,
            cameraPositionState = cameraPositionState,
            onMarkerClick = onMarkerClick
        )
    }
}