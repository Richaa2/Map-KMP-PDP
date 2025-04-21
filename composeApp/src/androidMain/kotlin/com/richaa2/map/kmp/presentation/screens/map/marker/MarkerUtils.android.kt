package com.richaa2.map.kmp.presentation.screens.map.marker

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.richaa2.map.kmp.presentation.screens.map.camera.NativeMap
import dev.icerock.moko.geo.LatLng
import mapkmp.composeapp.generated.resources.Res
import mapkmp.composeapp.generated.resources.end
import mapkmp.composeapp.generated.resources.start
import org.jetbrains.compose.resources.getString

actual object MarkerUtils {
    actual suspend fun addRouteMarkers(coordinates: List<LatLng>, nativeMap: NativeMap) {
        if (coordinates.isNotEmpty()) {
            val first = coordinates.first()
            val last = coordinates.last()
            nativeMap.addMarker(
                MarkerOptions()
                    .position(com.google.android.gms.maps.model.LatLng(first.latitude, first.longitude))
                    .title(getString(Res.string.start))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))

            )
            nativeMap.addMarker(
                MarkerOptions()
                    .position(com.google.android.gms.maps.model.LatLng(last.latitude, last.longitude))
                    .title(getString(Res.string.end))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))

            )
        }
    }
}