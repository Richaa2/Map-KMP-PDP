package com.richaa2.map.kmp.core

import com.richaa2.map.kmp.dependecies.DbClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.CoreLocation.kCLAuthorizationStatusRestricted
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.darwin.NSObject

actual class LocationManager actual constructor(dbClient: DbClient) {
    private val locationManagerDelegate = LocationManagerDelegate()
    actual val locationPermissionStatusState: StateFlow<LocationPermissionStatus>
        get() = locationManagerDelegate.locationPermissionStatusState

    actual fun requestLocationPermission() {
        locationManagerDelegate.requestLocationPermission()
    }

}

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
        println("requestLocationPermission ${getCurrentPermissionStatus()}")
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
        println("locationManagerDidChangeAuthorization 1: ${getCurrentPermissionStatus()}")

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