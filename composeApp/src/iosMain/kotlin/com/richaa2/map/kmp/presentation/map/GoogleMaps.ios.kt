package com.richaa2.map.kmp.presentation.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.GoogleMaps.GMSMapViewDelegateProtocol
import com.richaa2.map.kmp.domain.model.LatLong
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocationCoordinate2D
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun GoogleMaps(
    modifier: Modifier,
    onMapClick: (LatLong) -> Unit,
    onMapLongClick: (LatLong) -> Unit,
) {

    val mapView = remember { GMSMapView() }
    val cameraPosition = GMSCameraPosition.cameraWithLatitude(
        latitude = 1.35,
        longitude = 103.87,
        zoom = 18.0f
    )
    mapView.camera = cameraPosition
    val delegate = object : NSObject(), GMSMapViewDelegateProtocol {
        override fun mapView(
            mapView: GMSMapView,
            didLongPressAtCoordinate: CValue<CLLocationCoordinate2D>
        ) {
            val latLong = didLongPressAtCoordinate.useContents {
                LatLong(latitude = latitude, longitude = longitude)
            }
            println("Long pressed at: $latLong")
            onMapLongClick(latLong)
        }

    }
    mapView.delegate = delegate
    UIKitView(
        modifier = modifier.fillMaxSize(),
        factory = {
            mapView.apply {
                settings.compassButton = true
                myLocationEnabled = true
                settings.apply {
                    scrollGestures = true
                    zoomGestures = true
                    tiltGestures = true
                    rotateGestures = true
                }

            }
        },
        update = {}
    )

}
