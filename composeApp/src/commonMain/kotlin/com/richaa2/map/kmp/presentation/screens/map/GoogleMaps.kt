package com.richaa2.map.kmp.presentation.screens.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.richaa2.map.kmp.domain.model.CameraPosition
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.presentation.screens.map.camera.CameraPositionState
import dev.icerock.moko.geo.LatLng

@Composable
expect fun GoogleMaps(
    modifier: Modifier,
    savedLocations: List<LocationInfo>,
    onMarkerClick: (LocationInfo) -> Unit,
    onMapLongClick: (LatLong) -> Unit,
    cameraPositionState: CameraPositionState,
    getCameraPosition: suspend () -> CameraPosition,
    isLocationPermissionGranted: Boolean,
    routeCoordinates: List<LatLng>?,
    onSaveCameraPosition: (Double,Double, Float) -> Unit,
)