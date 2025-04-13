package com.richaa2.map.kmp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.presentation.AppViewModel
import com.richaa2.map.kmp.presentation.addLocation.AddLocationScreen
import com.richaa2.map.kmp.presentation.locationDetails.LocationDetailsScreen
import com.richaa2.map.kmp.presentation.map.MapScreen
import com.richaa2.map.kmp.presentation.map.camera.CameraPositionState
import com.richaa2.map.kmp.presentation.map.camera.CameraPositionStateSaver
import com.richaa2.map.kmp.presentation.map.utils.DEFAULT_MAP_LATITUDE
import com.richaa2.map.kmp.presentation.map.utils.DEFAULT_MAP_LONGITUDE
import com.richaa2.map.kmp.presentation.map.utils.DEFAULT_ZOOM_LEVEL
import com.richaa2.mappdp.navigation.Screen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI


@OptIn(KoinExperimentalAPI::class)
@Composable
fun AppNavGraph() {
    val appViewModel: AppViewModel = koinViewModel()

    val cameraPositionState = rememberSaveable(saver = CameraPositionStateSaver) {
        CameraPositionState(
            initialLatitude = DEFAULT_MAP_LATITUDE,
            initialLongitude = DEFAULT_MAP_LONGITUDE,
            initialZoom = DEFAULT_ZOOM_LEVEL
        )
    }

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Map()) {
        composable<Screen.Map> { backStackEntry ->
            val args = backStackEntry.toRoute<Screen.Map>()
            MapScreen(
                userPosition = LatLong.build(
                    args.userPositionLatitude,
                    args.userPositionLongitude
                ),
                routePosition = LatLong.build(
                    args.destinationPositionLatitude,
                    args.destinationPositionLongitude
                ),
                cameraPositionState = cameraPositionState,
                appViewModel = appViewModel,
                onAddLocation = { latLng ->
                    navController.navigate(
                        Screen.AddLocation(
                            latitude = latLng.latitude.toFloat(),
                            longitude = latLng.longitude.toFloat()
                        )
                    )
                },
                onLocationDetails = { location, userPosition ->
                    navController.navigate(
                        Screen.LocationDetails(
                            locationId = location.id,
                            userPositionLatitude = userPosition?.latitude?.toFloat(),
                            userPositionLongitude = userPosition?.longitude?.toFloat()
                        )
                    )
                },
                onMapScreen = {
                    navController.navigate(
                        Screen.Map(
                            userPositionLatitude = it?.latitude?.toFloat(),
                            userPositionLongitude = it?.longitude?.toFloat()
                        )
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
                userPosition = LatLong.build(args.userPositionLatitude, args.userPositionLongitude),
                onNavToRoute = { userPosition, destinationPosition ->
                    navController.navigate(
                        Screen.Map(
                            userPositionLatitude = userPosition.latitude.toFloat(),
                            userPositionLongitude = userPosition.longitude.toFloat(),
                            destinationPositionLatitude = destinationPosition.latitude.toFloat(),
                            destinationPositionLongitude = destinationPosition.longitude.toFloat()
                        )
                    )
                },
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