package com.richaa2.map.kmp.presentation.screens.map.camera

import androidx.compose.runtime.saveable.Saver
import kotlinx.coroutines.flow.StateFlow

/**
 * An abstraction of the camera state. Contains coordinates and zoom.
 */
expect class CameraPositionState() {

    fun animateTo(newLatitude: Double, newLongitude: Double, newZoom: Float, durationMillis: Int)
    /**
     * Binding of the native map (platform-dependent).
     */
    fun setNativeMap(nativeMap: NativeMap)


}
/**
 * An abstraction of the native map.
 *
 * Each platform's actual implementation of this class specifies the corresponding native map type
 * (e.g., GoogleMap for Android or GMSMapView for iOS).
 */
expect class NativeMap
/**
 * An abstraction of the native marker.
 *
 * This class is used to work with markers on the map in platform-specific code.
 */
expect class NativeMarker