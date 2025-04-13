package com.richaa2.map.kmp.presentation.map.marker

import cocoapods.GoogleMaps.GMSMarker
import com.richaa2.map.kmp.presentation.map.camera.NativeMap
import dev.icerock.moko.geo.LatLng
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.UIKit.UIColor

actual object MarkerUtils {
    @OptIn(ExperimentalForeignApi::class)
    actual fun addRouteMarkers(coordinates: List<LatLng>, nativeMap: NativeMap) {
        if (coordinates.isNotEmpty()) {
            val first = coordinates.first()
            val last = coordinates.last()
            val startMarker = GMSMarker().apply {
                position = CLLocationCoordinate2DMake(first.latitude, first.longitude)
                title = "Start"
                map = nativeMap
                icon = GMSMarker.markerImageWithColor(UIColor.greenColor)
            }
            val endMarker = GMSMarker().apply {
                position = CLLocationCoordinate2DMake(last.latitude, last.longitude)
                title = "End"
                map = nativeMap
                icon = GMSMarker.markerImageWithColor(UIColor.redColor)

            }


        }
    }
}