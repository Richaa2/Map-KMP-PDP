package com.richaa2.map.kmp.presentation.map.utils

import cocoapods.GoogleMaps.GMSCameraUpdate
import cocoapods.GoogleMaps.GMSCoordinateBounds
import cocoapods.GoogleMaps.animateWithCameraUpdate
import com.richaa2.map.kmp.presentation.map.camera.NativeMap
import com.richaa2.map.kmp.presentation.map.camera.NativeMarker
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGFloat

@OptIn(ExperimentalForeignApi::class)
actual fun fitCameraAllMarkers(markerList: List<NativeMarker>, map: NativeMap) {
    if (markerList.isEmpty()) return

    val bounds = markerList.map { it.position }
        .fold(null as GMSCoordinateBounds?) { acc, pos ->
            acc?.includingCoordinate(pos) ?: GMSCoordinateBounds(pos, pos)
        }

    bounds?.let {
        val padding: CGFloat = 100.0
        map.animateWithCameraUpdate(GMSCameraUpdate.fitBounds(it, padding))
    }
}