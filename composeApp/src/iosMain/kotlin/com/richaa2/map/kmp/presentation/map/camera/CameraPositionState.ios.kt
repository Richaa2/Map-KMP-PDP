package com.richaa2.map.kmp.presentation.map.camera

import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSCameraUpdate
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.GoogleMaps.animateWithCameraUpdate
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@OptIn(ExperimentalForeignApi::class)
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

    private var mapView: GMSMapView? = null

    actual fun setNativeMap(nativeMap: NativeMap) {
        mapView = nativeMap as? GMSMapView
    }

    actual fun updateCameraPosition(latitude: Double, longitude: Double, zoom: Float) {
        _latitude.value = latitude
        _longitude.value = longitude
        _zoom.value = zoom
    }

    actual fun animateTo(
        newLatitude: Double,
        newLongitude: Double,
        newZoom: Float,
        durationMillis: Int,
    ) {
        val newCameraPosition = GMSCameraPosition.cameraWithLatitude(
            latitude = newLatitude,
            longitude = newLongitude,
            zoom = newZoom
        )

        mapView?.animateWithCameraUpdate(GMSCameraUpdate.setCamera(newCameraPosition))
        updateCameraPosition(newLatitude, newLongitude, newZoom)
    }
}

@OptIn(ExperimentalForeignApi::class)
actual typealias NativeMap = GMSMapView

@OptIn(ExperimentalForeignApi::class)
actual typealias NativeMarker = cocoapods.GoogleMaps.GMSMarker