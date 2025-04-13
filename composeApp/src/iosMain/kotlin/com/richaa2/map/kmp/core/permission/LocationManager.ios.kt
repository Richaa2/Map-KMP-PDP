package com.richaa2.map.kmp.core.permission

import com.richaa2.map.kmp.core.LocationManagerDelegate
import kotlinx.coroutines.flow.StateFlow

actual class LocationManager {
    private val locationManagerDelegate = LocationManagerDelegate()
    actual val locationPermissionStatusState: StateFlow<LocationPermissionStatus>
        get() = locationManagerDelegate.locationPermissionStatusState

    actual fun requestLocationPermission() {
        locationManagerDelegate.requestLocationPermission()
    }

}