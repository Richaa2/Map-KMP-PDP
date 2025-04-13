package com.richaa2.map.kmp.presentation.map.marker

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.richaa2.map.kmp.presentation.map.camera.NativeMap
import dev.icerock.moko.geo.LatLng

actual object MarkerUtils {
    actual fun addRouteMarkers(coordinates: List<LatLng>, nativeMap: NativeMap) {
        if (coordinates.isNotEmpty()) {
            val first = coordinates.first()
            val last = coordinates.last()
            nativeMap.addMarker(
                MarkerOptions()
                    .position(com.google.android.gms.maps.model.LatLng(first.latitude, first.longitude))
                    .title("Start")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))

            )
            nativeMap.addMarker(
                MarkerOptions()
                    .position(com.google.android.gms.maps.model.LatLng(last.latitude, last.longitude))
                    .title("End")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))

            )
        }
    }
}