package com.richaa2.map.kmp.presentation.screens.map

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSMapStyle
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.Google_Maps_iOS_Utils.GMUClusterManager
import cocoapods.Google_Maps_iOS_Utils.GMUDefaultClusterIconGenerator
import cocoapods.Google_Maps_iOS_Utils.GMUDefaultClusterRenderer
import cocoapods.Google_Maps_iOS_Utils.GMUNonHierarchicalDistanceBasedAlgorithm
import com.richaa2.map.kmp.domain.model.CameraPosition
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.presentation.screens.map.camera.CameraPositionState
import com.richaa2.map.kmp.presentation.screens.map.clustering.IOSLocationClusterItem
import com.richaa2.map.kmp.presentation.screens.map.marker.MarkerUtils
import com.richaa2.map.kmp.presentation.screens.map.polyline.PolylineUtils
import com.richaa2.map.kmp.presentation.screens.map.utils.MapViewDelegate
import dev.icerock.moko.geo.LatLng
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.Foundation.NSBundle
import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.dataWithContentsOfURL


@OptIn(ExperimentalForeignApi::class, ExperimentalComposeUiApi::class)
@Composable
actual fun GoogleMaps(
    modifier: Modifier,
    savedLocations: List<LocationInfo>,
    onMarkerClick: (LocationInfo) -> Unit,
    onMapLongClick: (LatLong) -> Unit,
    cameraPositionState: CameraPositionState,
    getCameraPosition: suspend () -> CameraPosition,
    isLocationPermissionGranted: Boolean,
    routeCoordinates: List<LatLng>?,
    onSaveCameraPosition: (Double, Double, Float) -> Unit,
    ) {

    val mapView = remember { GMSMapView() }

    LifecycleEventEffect(
        event = Lifecycle.Event.ON_PAUSE,
        onEvent = {
            mapView.camera.target.useContents {
                onSaveCameraPosition(latitude, longitude, mapView.camera.zoom)
            }
        }
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
        remember { MapViewDelegate(onMapLongClick, onMarkerClick) }

    val isDarkTheme = isSystemInDarkTheme()
    LaunchedEffect(isDarkTheme) {
        setMapStyle(mapView, isDarkTheme)
    }

    LaunchedEffect(Unit) {
        cameraPositionState.setNativeMap(mapView)

        val cameraPosition = getCameraPosition()
        val gmsCameraPosition = GMSCameraPosition.cameraWithLatitude(
            latitude = cameraPosition.target.latitude,
            longitude = cameraPosition.target.longitude,
            zoom = cameraPosition.zoom
        )

        mapView.camera = gmsCameraPosition
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

    LaunchedEffect(routeCoordinates) {
        if (routeCoordinates?.isNotEmpty() == true) {
            PolylineUtils.drawPolyline(coordinates = routeCoordinates, map = mapView)
            PolylineUtils.fitCameraToPolyline(routeCoordinates, mapView)
            MarkerUtils.addRouteMarkers(routeCoordinates, mapView)
        }
    }
    UIKitView(
        modifier = modifier.fillMaxSize(),
        factory = {
            mapView.apply {
                settings.compassButton = false
                delegate = mapViewDelegate

            }
        },
        update = {
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

@OptIn(ExperimentalForeignApi::class)
private fun setMapStyle(mapView: GMSMapView, isSystemInDarkTheme: Boolean) {
    if (isSystemInDarkTheme) {
        val url = NSBundle.mainBundle
            .URLForResource("map_style_dark", withExtension = "json")
            ?: error("map_style_dark.json not found in bundle")
        val data = NSData.dataWithContentsOfURL(url)
            ?: error("can not download data from map_style_dark.json")
        val mapStyle = NSString.create(data, NSUTF8StringEncoding) as String
        mapView.setMapStyle(GMSMapStyle.styleWithJSONString(style = mapStyle, error = null))
    } else {
        mapView.setMapStyle(null)
    }
}
