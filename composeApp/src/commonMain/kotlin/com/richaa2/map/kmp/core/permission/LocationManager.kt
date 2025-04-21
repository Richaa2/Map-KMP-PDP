package com.richaa2.map.kmp.core.permission

import kotlinx.coroutines.flow.StateFlow

expect class LocationManager {
    val locationPermissionStatusState: StateFlow<LocationPermissionStatus>
    fun requestLocationPermission()

}