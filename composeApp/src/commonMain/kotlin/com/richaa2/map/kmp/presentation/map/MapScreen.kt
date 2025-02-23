package com.richaa2.map.kmp.presentation.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.presentation.map.components.MapFloatingActionButton
import com.richaa2.map.kmp.domain.model.LocationInfo
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    viewModel: MapViewModel = koinViewModel<MapViewModel>(),
    onAddLocation: (LatLong) -> Unit,
    onLocationDetails: (LocationInfo) -> Unit,
) {
//    val uiState by viewModel.uiState.collectAsState()
//    val errorMessage by viewModel.errorState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    var showPermissionDialog by remember { mutableStateOf(false) }

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
//                cameraPositionState = cameraPositionState,
//                currentLocation = currentLocation,
                onDisabledClick = {
                    onAddLocation(
                        LatLong(
                            latitude = 2.32, longitude = 2.32
                        )
                    )
//                    if (errorMessage == null) {
//                        viewModel.onLocationDisabledMessage()
//                    }
                }
            )
        },
    ) { paddingValues ->

        GoogleMaps(
            modifier = Modifier,
            onMapClick = { latLong ->
                println("onMapClick LatLong: $latLong")
//                onAddLocation(latLong)
            },
            onMapLongClick = { latLong ->
                println("onMapLongClick LatLong: $latLong")

                onAddLocation(latLong)
            }
        )

    }


}
