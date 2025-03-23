package com.richaa2.map.kmp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.richaa2.map.kmp.core.PermissionBridge
import com.richaa2.map.kmp.core.PermissionResultCallback
import com.richaa2.map.kmp.core.PermissionsBridgeListener
import dev.icerock.moko.geo.LocationTracker
import org.koin.android.ext.android.inject
import org.koin.core.context.GlobalContext

class MainActivity : ComponentActivity(), PermissionsBridgeListener {
    private val locationTracker: LocationTracker by inject()
    private val permissionBridge: PermissionBridge by inject()

    private var locationPermissionResultCallback: PermissionResultCallback? = null

    private val requestLocationPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                locationPermissionResultCallback?.onPermissionGranted()
            } else {
// TODO FIX PERMISSION AT COLD LAUNCH
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        permissionBridge.setListener(this)

        locationTracker.bind(this)
        setContent {
            App()
        }
    }

    override fun requestLocationPermission(callback: PermissionResultCallback) {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        when {
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                callback.onPermissionGranted()
            }

            shouldShowRequestPermissionRationale(permission) -> {
                callback.onPermissionDenied(false)
            }

            else -> {
                locationPermissionResultCallback = callback
                requestLocationPermissionLauncher.launch(permission)
            }
        }
    }

    override fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}
