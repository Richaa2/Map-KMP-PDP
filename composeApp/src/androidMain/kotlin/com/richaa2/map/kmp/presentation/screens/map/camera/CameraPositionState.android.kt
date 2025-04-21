package com.richaa2.map.kmp.presentation.screens.map.camera

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.richaa2.map.kmp.presentation.screens.map.utils.CLUSTER_ANIMATION_ZOOM_DURATION_MS

actual class CameraPositionState actual constructor() {

    private var googleMap: GoogleMap? = null

    actual fun setNativeMap(nativeMap: NativeMap) {
        googleMap = nativeMap as? GoogleMap
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