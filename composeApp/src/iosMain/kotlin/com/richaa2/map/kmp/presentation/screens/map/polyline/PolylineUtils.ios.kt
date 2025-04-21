package com.richaa2.map.kmp.presentation.screens.map.polyline

import cocoapods.GoogleMaps.GMSCameraUpdate
import cocoapods.GoogleMaps.GMSCoordinateBounds
import cocoapods.GoogleMaps.GMSMutablePath
import cocoapods.GoogleMaps.GMSPolyline
import cocoapods.GoogleMaps.includingPath
import com.richaa2.map.kmp.presentation.screens.map.camera.NativeMap
import dev.icerock.moko.geo.LatLng
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.CoreGraphics.CGFloat
import platform.UIKit.UIColor

actual object PolylineUtils {

    actual suspend fun decode(encodedPath: String): List<LatLng> {
        return withContext(Dispatchers.Default) {
            val len = encodedPath.length

            val path: MutableList<LatLng> =
                ArrayList()
            var index = 0
            var lat = 0
            var lng = 0

            while (index < len) {
                var result = 1
                var shift = 0
                var b: Int
                do {
                    b = encodedPath[index++].code - 63 - 1
                    result += b shl shift
                    shift += 5
                } while (b >= 0x1f)
                lat += if ((result and 1) != 0) (result shr 1).inv() else (result shr 1)

                result = 1
                shift = 0
                do {
                    b = encodedPath[index++].code - 63 - 1
                    result += b shl shift
                    shift += 5
                } while (b >= 0x1f)
                lng += if ((result and 1) != 0) (result shr 1).inv() else (result shr 1)

                path.add(LatLng(lat * 1e-5, lng * 1e-5))
            }

            path
        }

    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun drawPolyline(
        coordinates: List<LatLng>,
        map: NativeMap
    ) {
        val path = GMSMutablePath()
        coordinates.forEach { coord ->
            path.addLatitude(coord.latitude, longitude = coord.longitude)
        }
        val polyline = GMSPolyline()
        polyline.path = path
        polyline.strokeWidth = 5.0
        polyline.strokeColor = UIColor.blueColor
        polyline.geodesic = true
        polyline.map = map
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun fitCameraToPolyline(polylineCoordinates: List<LatLng>, nativeMap: NativeMap) {
        val path = GMSMutablePath()
        polylineCoordinates.forEach { point ->
            path.addLatitude(point.latitude, longitude = point.longitude)
        }
        val bounds = GMSCoordinateBounds().includingPath(path)

        val padding: CGFloat = 100.0
        nativeMap.setCameraTargetBounds(bounds)
        nativeMap.moveCamera(GMSCameraUpdate.fitBounds(bounds, padding))

    }

}