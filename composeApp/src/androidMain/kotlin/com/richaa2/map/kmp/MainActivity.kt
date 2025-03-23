package com.richaa2.map.kmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.richaa2.map.kmp.core.LocationManager
import dev.icerock.moko.geo.LocationTracker
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val locationTracker: LocationTracker by inject()
    private val locationManager : LocationManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        locationManager.setListener(this)
        locationTracker.bind(this)
        setContent {
            App()
        }
    }

}
