package com.richaa2.map.kmp.presentation.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSCameraUpdate
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.GoogleMaps.GMSMapViewDelegateProtocol
import cocoapods.GoogleMaps.GMSPanoramaCameraUpdate
import com.richaa2.map.kmp.domain.model.LatLong
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreLocation.CLLocationCoordinate2D
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun GoogleMaps(
    modifier: Modifier,
    onMapClick: (LatLong) -> Unit,
    onMapLongClick: (LatLong) -> Unit,
) {
    //TODO BUG Gestures do not work
    val mapView = remember { GMSMapView() }
    val cameraPosition = GMSCameraPosition.cameraWithLatitude(
        latitude = 1.35,
        longitude = 103.87,
        zoom = 18.0f
    )
    mapView.camera = cameraPosition
//    mapView.moveCamera(cameraUpdate)
    val delegate = object : NSObject(), GMSMapViewDelegateProtocol {
        override fun mapView(
            mapView: GMSMapView,
            didLongPressAtCoordinate: CValue<CLLocationCoordinate2D>
        ) {
        
            println("Long pressed at: ${didLongPressAtCoordinate}")

        }
      


    }
    mapView.delegate = delegate
    UIKitView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            mapView.apply {
//                camera = cameraPosition
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
        update = { view ->
            println("Update view: $view")
            view.settings.apply {
                scrollGestures = true
                zoomGestures = true
            }
        },
        properties = UIKitInteropProperties(
            isInteractive = true,
            isNativeAccessibilityEnabled = true
        )
    )

}
