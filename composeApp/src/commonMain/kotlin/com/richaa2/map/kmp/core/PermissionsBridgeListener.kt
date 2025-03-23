package com.richaa2.map.kmp.core

expect interface PermissionsBridgeListener {
    fun requestLocationPermission(callback: PermissionResultCallback)
    fun isLocationPermissionGranted(): Boolean
}

class PermissionBridge {

    private var listener: PermissionsBridgeListener? = null

    fun setListener(listener: PermissionsBridgeListener) {
        this.listener = listener
    }

    fun requestLocationPermission(callback: PermissionResultCallback) {
        listener?.requestLocationPermission(callback) ?: error("Callback handler not set")
    }

    fun isLocationPermissionGranted(): Boolean {
        return listener?.isLocationPermissionGranted() ?: false
    }

}

interface PermissionResultCallback {
    fun onPermissionGranted()
    fun onPermissionDenied(isPermanentDenied: Boolean)
}