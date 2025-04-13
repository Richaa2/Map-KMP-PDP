package com.richaa2.map.kmp.presentation.map.marker

import com.richaa2.map.kmp.presentation.map.camera.NativeMap
import dev.icerock.moko.geo.LatLng

expect object MarkerUtils {
     fun addRouteMarkers(coordinates: List<LatLng>, nativeMap: NativeMap)
}