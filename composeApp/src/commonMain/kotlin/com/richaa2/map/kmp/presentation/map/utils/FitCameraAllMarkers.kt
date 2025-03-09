package com.richaa2.map.kmp.presentation.map.utils

import com.richaa2.map.kmp.presentation.map.camera.NativeMap
import com.richaa2.map.kmp.presentation.map.camera.NativeMarker

expect fun fitCameraAllMarkers(
    markerList: List<NativeMarker>,
    map: NativeMap,
)
