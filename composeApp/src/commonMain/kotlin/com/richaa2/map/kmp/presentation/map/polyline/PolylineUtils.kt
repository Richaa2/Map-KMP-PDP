package com.richaa2.map.kmp.presentation.map.polyline

import com.richaa2.map.kmp.presentation.map.camera.NativeMap
import dev.icerock.moko.geo.LatLng

expect object PolylineUtils {
    suspend fun decode(encodedPath: String): List<LatLng>
    fun drawPolyline(coordinates: List<LatLng>, map: NativeMap)
    fun fitCameraToPolyline(polylineCoordinates: List<LatLng>, nativeMap: NativeMap)

}