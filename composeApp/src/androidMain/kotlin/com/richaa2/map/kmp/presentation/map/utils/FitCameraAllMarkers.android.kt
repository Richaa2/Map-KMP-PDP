package com.richaa2.map.kmp.presentation.map.utils

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.richaa2.map.kmp.presentation.map.camera.NativeMap
import com.richaa2.map.kmp.presentation.map.camera.NativeMarker

actual fun fitCameraAllMarkers(
    markerList: List<NativeMarker>,
    map: NativeMap,
) {
    if (markerList.isEmpty()) return

    val builder = LatLngBounds.Builder()
    markerList.forEach { marker ->
        builder.include(marker.position)
    }
    val bounds = builder.build()
    val padding = 100
    map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))

}