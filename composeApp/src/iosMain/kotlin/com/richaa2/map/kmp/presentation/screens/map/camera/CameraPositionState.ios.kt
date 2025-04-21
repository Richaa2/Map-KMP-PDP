package com.richaa2.map.kmp.presentation.screens.map.camera

import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSCameraUpdate
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.GoogleMaps.animateWithCameraUpdate
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
actual class CameraPositionState actual constructor() {

    private var mapView: GMSMapView? = null

    actual fun setNativeMap(nativeMap: NativeMap) {
        mapView = nativeMap as? GMSMapView
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
    }
}

@OptIn(ExperimentalForeignApi::class)
actual typealias NativeMap = GMSMapView

@OptIn(ExperimentalForeignApi::class)
actual typealias NativeMarker = cocoapods.GoogleMaps.GMSMarker