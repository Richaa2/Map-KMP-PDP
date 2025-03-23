package com.richaa2.map.kmp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.richaa2.map.kmp.core.PermissionBridge
import com.richaa2.map.kmp.core.PermissionResultCallback
import com.richaa2.map.kmp.presentation.addLocation.AddLocationScreen
import com.richaa2.map.kmp.presentation.locationDetails.LocationDetailsScreen
import com.richaa2.map.kmp.presentation.map.MapScreen
import com.richaa2.mappdp.navigation.Screen
import org.koin.compose.getKoin


@Composable
fun AppNavGraph() {
    val koin = getKoin()

//    var isLocationPermissionGranted by remember {
//        mutableStateOf(
//            koin.get<PermissionBridge>().isLocationPermissionGranted()
//        )
//    }

//    fun requestPermission() {
//        koin.get<PermissionBridge>()
//            .requestLocationPermission(object : PermissionResultCallback {
//                override fun onPermissionGranted() {
//                    isLocationPermissionGranted =
//                        koin.get<PermissionBridge>().isLocationPermissionGranted()
//                }
//
//                override fun onPermissionDenied(
//                    isPermanentDenied: Boolean
//                ) {
//                    isLocationPermissionGranted =
//                        koin.get<PermissionBridge>().isLocationPermissionGranted()
//                }
//            })
//    }

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Map) {
        composable<Screen.Map> {
            MapScreen(
                onAddLocation = { latLng ->
                    navController.navigate(
                        Screen.AddLocation(
                            latitude = latLng.latitude.toFloat(),
                            longitude = latLng.longitude.toFloat()
                        )
                    )
                },
                onLocationDetails = { location ->
                    navController.navigate(
                        Screen.LocationDetails(location.id)
                    )
                }
            )
        }
        composable<Screen.AddLocation> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.AddLocation>()
            AddLocationScreen(
                latitude = args.latitude.toDouble(),
                longitude = args.longitude.toDouble(),
                locationId = args.locationId,
                onBack = {
                    if (navController.previousBackStackEntry != null) navController.popBackStack()
                }
            )
        }
        composable<Screen.LocationDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.LocationDetails>()

            LocationDetailsScreen(
                locationId = args.locationId,
                onBack = {
                    if (navController.previousBackStackEntry != null) navController.popBackStack()
                },
                onEdit = { latLng, locationId ->
                    navController.navigate(
                        Screen.AddLocation(
                            latitude = latLng.latitude.toFloat(),
                            longitude = latLng.longitude.toFloat(),
                            locationId = locationId
                        )
                    )
                }
            )
        }
    }
}