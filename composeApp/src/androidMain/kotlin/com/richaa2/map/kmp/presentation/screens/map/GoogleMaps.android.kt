package com.richaa2.map.kmp.presentation.screens.map

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import com.richaa2.map.kmp.R
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.presentation.screens.map.camera.CameraPositionState
import com.richaa2.map.kmp.presentation.screens.map.clustering.LocationClusterItem
import com.richaa2.map.kmp.presentation.screens.map.clustering.LocationClustering
import com.richaa2.map.kmp.presentation.screens.map.marker.MarkerUtils
import com.richaa2.map.kmp.presentation.screens.map.polyline.PolylineUtils
import com.richaa2.map.kmp.presentation.screens.map.polyline.PolylineUtils.fitCameraToPolyline
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
    getCameraPosition: suspend () -> com.richaa2.map.kmp.domain.model.CameraPosition,
    isLocationPermissionGranted: Boolean,
    routeCoordinates: List<LatLng>?,
    onSaveCameraPosition: (Double, Double, Float) -> Unit,

    ) {

    val nativeCameraPositionState = rememberCameraPositionState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val cameraPosition = getCameraPosition()
        nativeCameraPositionState.position = CameraPosition(
            com.google.android.gms.maps.model.LatLng(
                cameraPosition.target.latitude,
                cameraPosition.target.longitude
            ),
            cameraPosition.zoom,
            0f,
            0f
        )
    }
    LifecycleEventEffect(
        event = Lifecycle.Event.ON_PAUSE,
        onEvent = {
            onSaveCameraPosition(
                nativeCameraPositionState.position.target.latitude,
                nativeCameraPositionState.position.target.longitude,
                nativeCameraPositionState.position.zoom
            )
        }
    )

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
        derivedStateOf { getMapProperties(isDarkTheme, context) }
    }

    GoogleMap(
        cameraPositionState = nativeCameraPositionState,
        modifier = modifier,
        uiSettings = uiSettings.value,
        properties = mapProperties,
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

private fun getMapProperties(isDarkTheme: Boolean, context: Context): MapProperties {
    val mapStyle = if (isDarkTheme) {
        MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style_dark)
    } else {
        null
    }
    return MapProperties(isMyLocationEnabled = false, mapStyleOptions = mapStyle)
}