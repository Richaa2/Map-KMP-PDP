package com.richaa2.map.kmp.presentation.screens.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.richaa2.map.kmp.core.permission.LocationManager
import com.richaa2.map.kmp.core.permission.LocationPermissionStatus
import com.richaa2.map.kmp.domain.model.CameraPosition
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.presentation.app.AppViewModel
import com.richaa2.map.kmp.presentation.screens.map.camera.CameraPositionState
import com.richaa2.map.kmp.presentation.screens.map.components.MapFloatingActionButton
import com.richaa2.map.kmp.presentation.screens.map.components.PermissionDeniedDialog
import com.richaa2.map.kmp.presentation.utils.LocalErrorFlow
import compose.icons.FeatherIcons
import compose.icons.feathericons.Home
import mapkmp.composeapp.generated.resources.Res
import mapkmp.composeapp.generated.resources.loading
import mapkmp.composeapp.generated.resources.map
import mapkmp.composeapp.generated.resources.route
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI


@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun MapScreen(
    locationManager: LocationManager = koinInject(),
    viewModel: MapViewModel = koinViewModel(),
    appViewModel: AppViewModel,
    userPosition: LatLong?,
    routePosition: LatLong?,
    cameraPositionState: CameraPositionState,
    getCameraPosition: suspend () -> CameraPosition,
    onMapScreen: (LatLong?) -> Unit,
    onAddLocation: (LatLong) -> Unit,
    onLocationDetails: (LocationInfo, LatLong?) -> Unit,
    onSaveCameraPosition: (Double, Double, Float) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val permissionStatus by locationManager.locationPermissionStatusState.collectAsState()

    val errorFlow = LocalErrorFlow.current

    var showPermissionDialog by remember { mutableStateOf(false) }

    val currentLocation by appViewModel.currentLocation.collectAsState(initial = userPosition)

    LaunchedEffect(Unit) {
        if (userPosition != null && routePosition != null) {
            viewModel.getRoute(
                origin = userPosition,
                destination = routePosition
            )
        } else {
            viewModel.loadSavedLocations()
        }
    }

    LaunchedEffect(permissionStatus) {
        if (permissionStatus == LocationPermissionStatus.NOT_DETERMINED) {
            showPermissionDialog = true
        }
        if (permissionStatus == LocationPermissionStatus.ACCEPTED) {
            viewModel.startLocationUpdates()
        }
    }

    if (showPermissionDialog) {
        PermissionDeniedDialog(
            onDismiss = { showPermissionDialog = false },
            onRequestPermission = {
                showPermissionDialog = false
                locationManager.requestLocationPermission()
            }
        )
    }

    LaunchedEffect(Unit) {
        viewModel.errorAction.collect {
            errorFlow.emit(it.snackbarMassage)
            when (it) {
                is MapViewModel.MapErrorAction.RouteError -> onMapScreen(currentLocation)
                else -> Unit
            }
        }
    }



    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    if (uiState is MapViewModel.MapUiState.RouteSuccess) {
                        IconButton(
                            onClick = { onMapScreen(currentLocation) }
                        ) {
                            Icon(
                                imageVector = FeatherIcons.Home,
                                tint = Color.White,
                                contentDescription = null
                            )
                        }
                    }
                },
                title = {
                    val text = when (uiState) {
                        MapViewModel.MapUiState.Loading -> Res.string.loading
                        is MapViewModel.MapUiState.MapSuccess -> Res.string.map
                        is MapViewModel.MapUiState.RouteSuccess -> Res.string.route
                    }
                    Text(stringResource(text))
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            if (uiState !is MapViewModel.MapUiState.Loading) {
                MapFloatingActionButton(
                    cameraPositionState = cameraPositionState,
                    currentLocation = currentLocation,
                    onDisabledClick = {
                        viewModel.onLocationDisabledMessage()
                    }
                )
            }
        },
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            when (uiState) {
                MapViewModel.MapUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    GoogleMaps(
                        modifier = Modifier,
                        savedLocations = (uiState as? MapViewModel.MapUiState.MapSuccess)?.locations
                            ?: emptyList(),
                        onMarkerClick = {
                            onLocationDetails(it, currentLocation)
                        },
                        onMapLongClick = { latLong ->
                            if (uiState is MapViewModel.MapUiState.MapSuccess) {
                                onAddLocation(latLong)
                            }
                        },
                        cameraPositionState = cameraPositionState,
                        isLocationPermissionGranted = permissionStatus == LocationPermissionStatus.ACCEPTED,
                        routeCoordinates = (uiState as? MapViewModel.MapUiState.RouteSuccess)?.routes,
                        getCameraPosition = getCameraPosition,
                        onSaveCameraPosition = onSaveCameraPosition
                    )
                }
            }

        }
    }
}
