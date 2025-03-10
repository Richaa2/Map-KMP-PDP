package com.richaa2.map.kmp.presentation.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.presentation.map.camera.CameraPositionState
import com.richaa2.map.kmp.presentation.map.camera.CameraPositionStateSaver
import com.richaa2.map.kmp.presentation.map.components.MapFloatingActionButton
import com.richaa2.map.kmp.presentation.map.utils.DEFAULT_MAP_LATITUDE
import com.richaa2.map.kmp.presentation.map.utils.DEFAULT_MAP_LONGITUDE
import com.richaa2.map.kmp.presentation.map.utils.DEFAULT_ZOOM_LEVEL
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI


@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun MapScreen(
    viewModel: MapViewModel = koinViewModel<MapViewModel>(),
    onAddLocation: (LatLong) -> Unit,
    onLocationDetails: (LocationInfo) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val errorMessage by viewModel.errorState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    var showPermissionDialog by remember { mutableStateOf(false) }

    val currentLocation by viewModel.currentLocation.collectAsState()
    LaunchedEffect(currentLocation) {
        println(
            "currentLocation $currentLocation"
        )
    }
    val cameraPositionState = rememberSaveable(saver = CameraPositionStateSaver) {
        CameraPositionState(
            initialLatitude = DEFAULT_MAP_LATITUDE,
            initialLongitude = DEFAULT_MAP_LONGITUDE,
            initialZoom = DEFAULT_ZOOM_LEVEL
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("MAP") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colors.primary,
                    titleContentColor = MaterialTheme.colors.onPrimary,
                    actionIconContentColor = MaterialTheme.colors.onPrimary
                )
            )
        },
        floatingActionButton = {

            MapFloatingActionButton(
                cameraPositionState = cameraPositionState,
                currentLocation = currentLocation,
                onDisabledClick = {

                    if (errorMessage == null) {
                        viewModel.onLocationDisabledMessage()
                    }
                }
            )
        },
    ) { paddingValues ->

        GoogleMaps(
            modifier = Modifier,
            savedLocations = (uiState as? MapViewModel.MapUiState.Success)?.locations ?: emptyList(),
            onMarkerClick = {
                onLocationDetails(it)
            },
            onMapLongClick = { latLong ->
                println("onMapLongClick LatLong: $latLong")

                onAddLocation(latLong)
            },
            cameraPositionState = cameraPositionState
        )

    }


}
