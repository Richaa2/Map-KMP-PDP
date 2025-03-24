package com.richaa2.map.kmp.core

import com.richaa2.map.kmp.dependecies.DbClient
import kotlinx.coroutines.flow.StateFlow

expect class LocationManager {
    val locationPermissionStatusState: StateFlow<LocationPermissionStatus>
    fun requestLocationPermission()

}