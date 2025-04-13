package com.richaa2.map.kmp.core

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.richaa2.map.kmp.platform.DbClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

actual class LocationManager (private val dbClient: DbClient) {

    private val _locationPermissionStatusState = MutableStateFlow(getCurrentPermissionStatus())
    actual val locationPermissionStatusState: StateFlow<LocationPermissionStatus> =
        _locationPermissionStatusState.asStateFlow()

    private var requestLocationPermissionLauncher: ActivityResultLauncher<String>? = null

    actual fun requestLocationPermission() {
        requestLocationPermissionLauncher?.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            dbClient.context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getCurrentPermissionStatus(): LocationPermissionStatus {
        return if (isPermissionGranted()) {
            LocationPermissionStatus.ACCEPTED
        } else {
            LocationPermissionStatus.NOT_DETERMINED
        }
    }

    fun setListener(componentActivity: ComponentActivity) {
        requestLocationPermissionLauncher =
            componentActivity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    _locationPermissionStatusState.value = LocationPermissionStatus.ACCEPTED
                } else {
                    _locationPermissionStatusState.value =
                        LocationPermissionStatus.RESTRICTED_OR_DENIED
                }
            }
    }
}