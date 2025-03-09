package com.richaa2.map.kmp.presentation.map.camera

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.richaa2.map.kmp.presentation.map.utils.CLUSTER_ANIMATION_ZOOM_DURATION_MS
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

actual class CameraPositionState actual constructor(
    initialLatitude: Double,
    initialLongitude: Double,
    initialZoom: Float
) {
    private val _latitude = MutableStateFlow(initialLatitude)
    private val _longitude = MutableStateFlow(initialLongitude)
    private val _zoom = MutableStateFlow(initialZoom)

    actual var latitude = _latitude.asStateFlow()

    actual var longitude = _longitude.asStateFlow()

    actual var zoom = _zoom.asStateFlow()

    private var googleMap: GoogleMap? = null

    actual fun setNativeMap(nativeMap: NativeMap) {
        googleMap = nativeMap as? GoogleMap
    }

    actual fun updateCameraPosition(latitude: Double, longitude: Double, zoom: Float) {
        _latitude.value = latitude
        _longitude.value = longitude
        _zoom.value = zoom
    }

    actual fun animateTo(newLatitude: Double, newLongitude: Double, newZoom: Float, durationMillis: Int) {
        val target = LatLng(newLatitude, newLongitude)
        val newCameraPosition = CameraPosition.Builder()
            .target(target)
            .zoom(newZoom)
            .build()
        googleMap?.animateCamera(
            CameraUpdateFactory.newCameraPosition(newCameraPosition),
            durationMillis,
            null
        )
        updateCameraPosition(newLatitude, newLongitude, newZoom)
    }

     fun animateWithBounds(latLngBounds: LatLngBounds, boundsPadding: Int) {
        googleMap?.animateCamera(
            CameraUpdateFactory.newLatLngBounds(latLngBounds, boundsPadding),
            CLUSTER_ANIMATION_ZOOM_DURATION_MS,
            null
        )
    }
}

actual typealias NativeMap = GoogleMap

actual typealias NativeMarker = com.google.android.gms.maps.model.Marker