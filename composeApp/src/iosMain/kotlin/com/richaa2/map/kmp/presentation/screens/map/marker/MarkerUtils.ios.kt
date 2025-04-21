package com.richaa2.map.kmp.presentation.screens.map.marker

import cocoapods.GoogleMaps.GMSMarker
import com.richaa2.map.kmp.presentation.screens.map.camera.NativeMap
import dev.icerock.moko.geo.LatLng
import kotlinx.cinterop.ExperimentalForeignApi
import mapkmp.composeapp.generated.resources.Res
import mapkmp.composeapp.generated.resources.end
import mapkmp.composeapp.generated.resources.start
import org.jetbrains.compose.resources.getString
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.UIKit.UIColor

actual object MarkerUtils {
    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun addRouteMarkers(coordinates: List<LatLng>, nativeMap: NativeMap) {
        if (coordinates.isNotEmpty()) {
            val first = coordinates.first()
            val last = coordinates.last()
            val startMarker = GMSMarker().apply {
                position = CLLocationCoordinate2DMake(first.latitude, first.longitude)
                title = getString(Res.string.start)
                map = nativeMap
                icon = GMSMarker.markerImageWithColor(UIColor.greenColor)
            }
            val endMarker = GMSMarker().apply {
                position = CLLocationCoordinate2DMake(last.latitude, last.longitude)
                title = getString(Res.string.end)
                map = nativeMap
                icon = GMSMarker.markerImageWithColor(UIColor.redColor)

            }


        }
    }
}