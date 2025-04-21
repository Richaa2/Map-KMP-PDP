package com.richaa2.map.kmp.presentation.screens.map.utils

import cocoapods.GoogleMaps.GMSCameraUpdate
import cocoapods.GoogleMaps.GMSCoordinateBounds
import cocoapods.GoogleMaps.animateWithCameraUpdate
import com.richaa2.map.kmp.presentation.screens.map.camera.NativeMap
import com.richaa2.map.kmp.presentation.screens.map.camera.NativeMarker
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
        val padding: CGFloat = DEFAULT_MAP_PADDING
        map.animateWithCameraUpdate(GMSCameraUpdate.fitBounds(it, padding))
    }
}