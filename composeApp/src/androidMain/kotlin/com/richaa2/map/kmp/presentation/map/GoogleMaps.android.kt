package com.richaa2.map.kmp.presentation.map

import MAP_STYLE_JSON
import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.presentation.map.camera.CameraPositionState
import com.richaa2.map.kmp.presentation.map.clustering.LocationClusterItem
import com.richaa2.map.kmp.presentation.map.clustering.LocationClustering
import com.richaa2.map.kmp.presentation.map.marker.MarkerUtils
import com.richaa2.map.kmp.presentation.map.polyline.PolylineUtils
import com.richaa2.map.kmp.presentation.map.polyline.PolylineUtils.fitCameraToPolyline
import dev.icerock.moko.geo.LatLng

@OptIn(MapsComposeExperimentalApi::class)
@SuppressLint("MissingPermission")
@Composable
actual fun GoogleMaps(
    modifier: Modifier,
    savedLocations: List<LocationInfo>,
    onMarkerClick: (LocationInfo) -> Unit,
    onMapLongClick: (LatLong) -> Unit,
    cameraPositionState: CameraPositionState,
    isLocationPermissionGranted: Boolean,
    routeCoordinates: List<LatLng>?
) {

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setNavigationBarColor(
            color = Color.Transparent
        )
    }

    val nativeCameraPositionState = rememberCameraPositionState().apply {
        position = CameraPosition(
            com.google.android.gms.maps.model.LatLng(
                cameraPositionState.latitude.value,
                cameraPositionState.longitude.value
            ),
            cameraPositionState.zoom.value,
            0f,
            0f
        )
    }

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
    val isDarkTheme = isSystemInDarkTheme()
    val mapProperties by remember(isDarkTheme) {
        derivedStateOf { getMapProperties(isDarkTheme) }
    }

    GoogleMap(
        cameraPositionState = nativeCameraPositionState,
        modifier = modifier,
        uiSettings = uiSettings.value,
        properties = mapProperties,
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
            cameraPositionState.setNativeMap(it)
        }

        MapEffect(routeCoordinates) {
            if (routeCoordinates?.isNotEmpty() == true) {
                PolylineUtils.drawPolyline(coordinates = routeCoordinates, map = it)
                fitCameraToPolyline(routeCoordinates, it)
                MarkerUtils.addRouteMarkers(routeCoordinates, it)

            }
        }

        MapEffect(isLocationPermissionGranted) {
            it.isMyLocationEnabled = isLocationPermissionGranted
        }

        LocationClustering(
            clusterItems = clusterItems,
            cameraPositionState = cameraPositionState,
            onMarkerClick = onMarkerClick
        )
    }
}

private fun getMapProperties(isDarkTheme: Boolean): MapProperties {
    val mapStyle = if (isDarkTheme) {
        MapStyleOptions(MAP_STYLE_JSON)
    } else {
        null
    }
    return MapProperties(isMyLocationEnabled = false, mapStyleOptions = mapStyle)
}