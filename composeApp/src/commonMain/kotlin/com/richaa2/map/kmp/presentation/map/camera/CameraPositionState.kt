package com.richaa2.map.kmp.presentation.map.camera

import androidx.compose.runtime.saveable.Saver
import kotlinx.coroutines.flow.StateFlow

/**
 * An abstraction of the camera state. Contains coordinates and zoom.
 */
expect class CameraPositionState(
    initialLatitude: Double,
    initialLongitude: Double,
    initialZoom: Float
) {
    var latitude: StateFlow<Double>
    var longitude: StateFlow<Double>
    var zoom: StateFlow<Float>


    fun animateTo(newLatitude: Double, newLongitude: Double, newZoom: Float, durationMillis: Int)
    /**
     * Binding of the native map (platform-dependent).
     */
    fun setNativeMap(nativeMap: NativeMap)

    fun updateCameraPosition(latitude: Double, longitude: Double, zoom: Float)

}
/**
 * A Saver for [CameraPositionState].
 *
 * This saver serializes the camera state (latitude, longitude, and zoom) into a [Map] and restores it.
 */
val CameraPositionStateSaver: Saver<CameraPositionState, Map<String, Any>> = Saver(
    save = { state ->
        mapOf(
            "latitude" to state.latitude.value,
            "longitude" to state.longitude.value,
            "zoom" to state.zoom.value
        )
    },
    restore = { restoredMap ->
        CameraPositionState(
            initialLatitude = restoredMap["latitude"] as Double,
            initialLongitude = restoredMap["longitude"] as Double,
            initialZoom = (restoredMap["zoom"] as Number).toFloat()
        )
    }
)

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