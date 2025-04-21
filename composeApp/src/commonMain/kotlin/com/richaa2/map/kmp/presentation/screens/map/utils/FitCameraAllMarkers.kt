package com.richaa2.map.kmp.presentation.screens.map.utils

import com.richaa2.map.kmp.presentation.screens.map.camera.NativeMap
import com.richaa2.map.kmp.presentation.screens.map.camera.NativeMarker

expect fun fitCameraAllMarkers(
    markerList: List<NativeMarker>,
    map: NativeMap,
)
