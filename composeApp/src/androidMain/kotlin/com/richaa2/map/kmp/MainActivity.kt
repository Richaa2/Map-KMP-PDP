package com.richaa2.map.kmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.icerock.moko.geo.LocationTracker
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val locationTracker: LocationTracker by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        locationTracker.bind(this)
        setContent {
            App()
        }
    }
}
