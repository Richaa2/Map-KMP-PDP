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
import cocoapods.GoogleMaps.GMSCameraUpdate
import cocoapods.GoogleMaps.GMSCoordinateBounds
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.GoogleMaps.GMSMapViewDelegateProtocol
import cocoapods.GoogleMaps.GMSMarker
import cocoapods.GoogleMaps.animateWithCameraUpdate
import cocoapods.Google_Maps_iOS_Utils.GMUClusterManager
import cocoapods.Google_Maps_iOS_Utils.GMUDefaultClusterIconGenerator
import cocoapods.Google_Maps_iOS_Utils.GMUDefaultClusterRenderer
import cocoapods.Google_Maps_iOS_Utils.GMUNonHierarchicalDistanceBasedAlgorithm
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.presentation.map.clustering.IOSLocationClusterItem
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGFloat
import platform.CoreLocation.CLLocationCoordinate2D
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.Foundation.NSArray
import platform.darwin.NSObject
import platform.darwin.NSUInteger


@OptIn(ExperimentalForeignApi::class, ExperimentalComposeUiApi::class)
@Composable
actual fun GoogleMaps(
    modifier: Modifier,
    savedLocations: List<LocationInfo>,
    onMarkerClick: (LocationInfo) -> Unit,
    onMapLongClick: (LatLong) -> Unit,
) {
    val mapView = remember { GMSMapView() }

    val cameraPosition = GMSCameraPosition.cameraWithLatitude(
        latitude = 1.35,
        longitude = 103.87,
        zoom = 18.0f
    )
    mapView.camera = cameraPosition

    val algorithm = remember { GMUNonHierarchicalDistanceBasedAlgorithm()}
    val iconGenerator = remember { GMUDefaultClusterIconGenerator() }
    val renderer =
        remember {GMUDefaultClusterRenderer(mapView as cocoapods.Google_Maps_iOS_Utils.GMSMapView, iconGenerator) }
    val clusterManager = remember {
        GMUClusterManager(mapView as cocoapods.Google_Maps_iOS_Utils.GMSMapView, algorithm, renderer)
    }

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


        override fun mapView(mapView: GMSMapView, didTapMarker: GMSMarker): Boolean {
            println("didTapMarker: $didTapMarker")
            val userData = didTapMarker.userData

            when (userData) {
                is IOSLocationClusterItem -> {
                    // If this is a separate cluster element, call onMarkerClick
                    onMarkerClick(userData.locationInfo)
                    return true
                }

                is cocoapods.Google_Maps_iOS_Utils.GMUStaticCluster -> {
                    // If this is a cluster
                    val items = userData.items as NSArray
                    if (items.count.toInt() > 0) {
                        var minLat = Double.MAX_VALUE
                        var maxLat = -Double.MAX_VALUE
                        var minLng = Double.MAX_VALUE
                        var maxLng = -Double.MAX_VALUE

                        for (i in 0 until items.count.toInt()) {
                            val index: NSUInteger = i.toULong()
                            val obj = items.objectAtIndex(index)
                            val clusterItem = obj as? IOSLocationClusterItem
                            if (clusterItem != null) {
                                // Get the coordinates of the element
                                val lat = clusterItem.position().useContents { latitude }
                                val lng = clusterItem.position().useContents { longitude }
                                if (lat < minLat) minLat = lat
                                if (lat > maxLat) maxLat = lat
                                if (lng < minLng) minLng = lng
                                if (lng > maxLng) maxLng = lng
                            }
                        }

                        // Create two extreme points: bottom-left and top-right
                        val bottomLeft = CLLocationCoordinate2DMake(minLat, minLng)
                        val topRight = CLLocationCoordinate2DMake(maxLat, maxLng)

                        // Create borders for the camera
                        val bounds = GMSCoordinateBounds(
                            bottomLeft,
                            topRight
                        )
                        // Animate the camera with the specified borders and 100 padding
                        val padding: CGFloat = 100.0

                        mapView.animateWithCameraUpdate(GMSCameraUpdate.fitBounds(bounds, padding))
                    }
                    return true // Return true to override the standard behavior (zoom)
                }
                else -> return false
            }
        }
    }

    LaunchedEffect(Unit) {
        mapView.delegate = delegate
        clusterManager.clearItems()
        val newItems = savedLocations.map { IOSLocationClusterItem(it) }
        clusterManager.addItems(newItems)
        clusterManager.cluster()
    }
    UIKitView(
        modifier = modifier.fillMaxSize(),
        factory = {
            (mapView as GMSMapView).apply {
                settings.compassButton = false
                myLocationEnabled = false
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