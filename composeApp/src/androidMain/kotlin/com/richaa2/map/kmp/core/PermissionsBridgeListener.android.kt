package com.richaa2.map.kmp.core

actual interface PermissionsBridgeListener {
    actual fun isLocationPermissionGranted(): Boolean
    actual fun requestLocationPermission(callback: PermissionResultCallback)
}