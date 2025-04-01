package com.richaa2.map.kmp.presentation.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.Google_Maps_iOS_Utils.GMUClusterManager
import cocoapods.Google_Maps_iOS_Utils.GMUDefaultClusterIconGenerator
import cocoapods.Google_Maps_iOS_Utils.GMUDefaultClusterRenderer
import cocoapods.Google_Maps_iOS_Utils.GMUNonHierarchicalDistanceBasedAlgorithm
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.presentation.map.camera.CameraPositionState
import com.richaa2.map.kmp.presentation.map.clustering.IOSLocationClusterItem
import com.richaa2.map.kmp.presentation.map.polyline.PolylineUtils
import com.richaa2.map.kmp.presentation.map.utils.MapViewDelegate
import dev.icerock.moko.geo.LatLng
import kotlinx.cinterop.ExperimentalForeignApi


@OptIn(ExperimentalForeignApi::class, ExperimentalComposeUiApi::class)
@Composable
actual fun GoogleMaps(
    modifier: Modifier,
    savedLocations: List<LocationInfo>,
    onMarkerClick: (LocationInfo) -> Unit,
    onMapLongClick: (LatLong) -> Unit,
    cameraPositionState: CameraPositionState,
    isLocationPermissionGranted: Boolean,
    polylineCoordinates: List<LatLng>?
) {
    val mapView = remember { GMSMapView() }

    val cameraPosition = GMSCameraPosition.cameraWithLatitude(
        latitude = cameraPositionState.latitude.value,
        longitude = cameraPositionState.longitude.value,
        zoom = cameraPositionState.zoom.value
    )

    val clusterManager = remember {
        val algorithm = GMUNonHierarchicalDistanceBasedAlgorithm()
        val iconGenerator = GMUDefaultClusterIconGenerator()
        val renderer = GMUDefaultClusterRenderer(
            mapView as cocoapods.Google_Maps_iOS_Utils.GMSMapView,
            iconGenerator
        )
        GMUClusterManager(
            mapView as cocoapods.Google_Maps_iOS_Utils.GMSMapView,
            algorithm,
            renderer
        )
    }
    val mapViewDelegate =
        remember { MapViewDelegate(onMapLongClick, onMarkerClick, cameraPositionState) }


    LaunchedEffect(Unit) {
        cameraPositionState.setNativeMap(mapView)
        mapView.camera = cameraPosition
        mapView.delegate = mapViewDelegate

        clusterManager.setMapDelegate(mapViewDelegate as cocoapods.Google_Maps_iOS_Utils.GMSMapViewDelegateProtocol)

        clusterManager.clearItems()
        val newItems = savedLocations.map { IOSLocationClusterItem(it) }
        clusterManager.addItems(newItems)
        clusterManager.cluster()
    }
    LaunchedEffect(isLocationPermissionGranted) {
        mapView.myLocationEnabled = isLocationPermissionGranted
    }

    LaunchedEffect(polylineCoordinates) {
        if (polylineCoordinates?.isNotEmpty() == true) {
            PolylineUtils.drawPolyline(coordinates = polylineCoordinates, map = mapView)
            PolylineUtils.fitCameraToPolyline(polylineCoordinates, mapView)
        }
    }
    UIKitView(
        modifier = modifier.fillMaxSize(),
        factory = {
            println("factory mapView")
            mapView.apply {
                settings.compassButton = false
                delegate = mapViewDelegate

            }
        },
        update = {
            println("update mapView")
            clusterManager.clearItems()
            val newItems = savedLocations.map { IOSLocationClusterItem(it) }
            clusterManager.addItems(newItems)
            clusterManager.cluster()
        },
        properties = UIKitInteropProperties(
            interactionMode = UIKitInteropInteractionMode.NonCooperative,
            isNativeAccessibilityEnabled = true
        ),

        )


}
