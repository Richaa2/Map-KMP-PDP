package com.richaa2.map.kmp.core

import com.richaa2.map.kmp.core.permission.LocationPermissionStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusRestricted
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.darwin.NSObject

class LocationManagerDelegate : NSObject(), CLLocationManagerDelegateProtocol {
    private val locationManager = CLLocationManager()

    private val _locationPermissionStatusState =
        MutableStateFlow(getCurrentPermissionStatus())
    val locationPermissionStatusState = _locationPermissionStatusState.asStateFlow()


    init {
        locationManager.delegate = this
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
    }

    @Throws(Exception::class)
    fun requestLocationPermission() {
        locationManager.requestWhenInUseAuthorization()
    }

    @Throws(Exception::class)
    private fun getCurrentPermissionStatus(): LocationPermissionStatus {
        return when (CLLocationManager.authorizationStatus()) {

            kCLAuthorizationStatusRestricted, kCLAuthorizationStatusDenied -> {
                LocationPermissionStatus.RESTRICTED_OR_DENIED
            }

            kCLAuthorizationStatusAuthorizedWhenInUse, kCLAuthorizationStatusAuthorizedAlways -> {
                LocationPermissionStatus.ACCEPTED
            }


            else -> LocationPermissionStatus.NOT_DETERMINED
        }

    }

    override fun locationManagerDidChangeAuthorization(manager: CLLocationManager) {
        _locationPermissionStatusState.value = when (manager.authorizationStatus) {
            kCLAuthorizationStatusRestricted,
            kCLAuthorizationStatusDenied,
            -> LocationPermissionStatus.RESTRICTED_OR_DENIED


            kCLAuthorizationStatusAuthorizedAlways,
            kCLAuthorizationStatusAuthorizedWhenInUse,
            -> LocationPermissionStatus.ACCEPTED

            else -> LocationPermissionStatus.NOT_DETERMINED
        }

    }

}