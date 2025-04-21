package com.richaa2.map.kmp.presentation.screens.map.marker

import com.richaa2.map.kmp.presentation.screens.map.camera.NativeMap
import dev.icerock.moko.geo.LatLng

expect object MarkerUtils {
     suspend fun addRouteMarkers(coordinates: List<LatLng>, nativeMap: NativeMap)
}