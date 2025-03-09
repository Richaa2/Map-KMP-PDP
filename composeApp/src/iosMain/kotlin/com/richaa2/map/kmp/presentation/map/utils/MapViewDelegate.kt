package com.richaa2.map.kmp.presentation.map.utils

import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.GoogleMaps.GMSMapViewDelegateProtocol
import cocoapods.GoogleMaps.GMSMarker
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.presentation.map.camera.CameraPositionState
import com.richaa2.map.kmp.presentation.map.clustering.IOSLocationClusterItem
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocationCoordinate2D
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
class MapViewDelegate(
    private val onMapLongClick: (LatLong) -> Unit,
    private val onMarkerClick: (LocationInfo) -> Unit,
    private val cameraPositionState: CameraPositionState
) : NSObject(), GMSMapViewDelegateProtocol {

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

    override fun mapView(mapView: GMSMapView, didTapMarker: GMSMarker): Boolean {
        println("didTapMarker: $didTapMarker")
        val userData = didTapMarker.userData

        return when (userData) {
            is IOSLocationClusterItem -> {
                onMarkerClick(userData.locationInfo)
                true
            }
            is cocoapods.Google_Maps_iOS_Utils.GMUStaticCluster -> {

                fitCameraAllMarkers(userData.items as List<GMSMarker>, mapView)
                true
            }
            else -> false
        }
    }

    override fun mapView(mapView: GMSMapView, didChangeCameraPosition: GMSCameraPosition) {
        println("Camera changed: $didChangeCameraPosition")
        didChangeCameraPosition.target.useContents {
            cameraPositionState.apply {
                updateCameraPosition(
                    latitude = this@useContents.latitude,
                    longitude = this@useContents.longitude,
                    zoom = didChangeCameraPosition.zoom
                )
            }
        }

    }
}