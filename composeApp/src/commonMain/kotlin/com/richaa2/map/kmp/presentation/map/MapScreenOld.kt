//package com.richaa2.mappdp.presentation.map
//
//import android.Manifest
//import android.content.Context
//import android.util.Log
//import androidx.compose.foundation.isSystemInDarkTheme
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.Button
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.SnackbarHost
//import androidx.compose.material3.SnackbarHostState
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.google.accompanist.permissions.ExperimentalPermissionsApi
//import com.google.accompanist.permissions.rememberPermissionState
//import com.google.accompanist.permissions.shouldShowRationale
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MapStyleOptions
//import com.google.maps.android.compose.CameraPositionState
//import com.google.maps.android.compose.GoogleMap
//import com.google.maps.android.compose.MapProperties
//import com.google.maps.android.compose.MapUiSettings
//import com.google.maps.android.compose.rememberCameraPositionState
//import com.richaa2.map.kmp.dependecies.MapViewModel
//import com.richaa2.mappdp.R
//import com.richaa2.mappdp.designsystem.components.LoadingContent
//import com.richaa2.mappdp.designsystem.theme.MapPDPTheme
//import com.richaa2.mappdp.domain.model.LocationInfo
//import com.richaa2.mappdp.presentation.map.components.LocationClustering
//import com.richaa2.map.kmp.presentation.map.components.MapFloatingActionButton
//import com.richaa2.mappdp.presentation.map.components.PermissionDeniedDialog
//import com.richaa2.mappdp.presentation.model.LocationClusterItem
//
//@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
//@Composable
//fun MapScreen(
//    viewModel: MapViewModel = hiltViewModel(),
//    onAddLocation: (LatLng) -> Unit,
//    onLocationDetails: (LocationInfo) -> Unit,
//) {
//    val uiState by viewModel.uiState.collectAsState()
//    val errorMessage by viewModel.errorState.collectAsState()
//
//    val snackbarHostState = remember { SnackbarHostState() }
//
//    val context = LocalContext.current
//    val isDarkTheme = isSystemInDarkTheme()
//    val mapProperties = remember {
//        mutableStateOf(getMapProperties(context, isDarkTheme))
//    }
//    val uiSettings = remember {
//        mutableStateOf(MapUiSettings(zoomControlsEnabled = false, myLocationButtonEnabled = false))
//    }
//
//    val cameraPositionState = rememberCameraPositionState()
//    var showPermissionDialog by remember { mutableStateOf(false) }
//
//    val locationPermissionState = rememberPermissionState(
//        permission = Manifest.permission.ACCESS_FINE_LOCATION,
//        onPermissionResult = {
//            if (it) {
//                showPermissionDialog = false
//                mapProperties.value = mapProperties.value.copy(isMyLocationEnabled = true)
//                viewModel.startLocationUpdates()
//            } else {
//                showPermissionDialog = true
//            }
//        }
//    )
//    Log.d("TAG", "Map start: ");
//    LaunchedEffect(errorMessage) {
//        errorMessage?.let {
//            snackbarHostState.showSnackbar(it)
//            viewModel.clearErrorMessage()
//        }
//    }
//
//    LaunchedEffect(Unit) {
//        locationPermissionState.launchPermissionRequest()
//    }
//
//    val currentLocation by viewModel.currentLocation.collectAsState()
//    if (showPermissionDialog && locationPermissionState.status.shouldShowRationale) {
//        PermissionDeniedDialog(
//            onDismiss = { showPermissionDialog = false },
//            onRequestPermission = { locationPermissionState.launchPermissionRequest() }
//        )
//    }
//    var count by remember {
//        mutableStateOf(0)
//    }
//    Scaffold(
//        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = { Text(stringResource(R.string.map)) },
//                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primary,
//                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
//                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
//                )
//            )
//        },
//        floatingActionButton = {
//
//            MapFloatingActionButton(
//                cameraPositionState = cameraPositionState,
//                currentLocation = currentLocation,
//                onDisabledClick = {
//                    if (errorMessage == null) {
//                        viewModel.onLocationDisabledMessage()
//                    }
//                }
//            )
//        },
//        content = { paddingValues ->
//            Log.d("TAG", "content: ");
//            when (val state = uiState) {
//                is MapViewModel.MapUiState.Loading -> {
//                    LoadingContent(innerPadding = paddingValues)
//                }
//
//                is MapViewModel.MapUiState.Success -> {
//                    MapContent(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(paddingValues),
//                        mapProperties = mapProperties.value,
//                        uiSettings = uiSettings.value,
//                        cameraPositionState = cameraPositionState,
//                        savedLocations = state.locations,
//                        onMapLongClick = { latLng -> onAddLocation(latLng) },
//                        onMarkerClick = { location -> onLocationDetails(location) }
//                    )
//
//                }
//            }
//
//        }
//
//    )
//    TestRecomposition()
//}
//
//@Composable
//fun TestRecomposition(modifier: Modifier = Modifier) {
//    var count by remember {
//        mutableStateOf(0)
//    }
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "$count")
//
//        Button(onClick = { count++ }, modifier = Modifier.size(200.dp)) {
//            Text(text = "Click")
//        }
//    }
//}
//@Composable
//fun MapContent(
//    modifier: Modifier = Modifier,
//    mapProperties: MapProperties,
//    uiSettings: MapUiSettings,
//    cameraPositionState: CameraPositionState,
//    savedLocations: List<LocationInfo>,
//    onMapLongClick: (LatLng) -> Unit,
//    onMarkerClick: (LocationInfo) -> Unit,
//) {
//    val clusterItems = remember(savedLocations) {
//        savedLocations.map { locationInfo ->
//            LocationClusterItem(locationInfo)
//        }
//    }
//    Box(modifier = modifier.fillMaxSize()) {
//
//        GoogleMap(
//            modifier = Modifier.fillMaxSize(),
//            properties = mapProperties,
//            uiSettings = uiSettings,
//            cameraPositionState = cameraPositionState,
//            onMapLongClick = onMapLongClick,
//            onMapLoaded = { Log.d("TAG", "loaded: map"); }
//        ) {
//            LocationClustering(
//                clusterItems = clusterItems,
//                cameraPositionState = cameraPositionState,
//                onMarkerClick = onMarkerClick
//            )
//        }
//    }
//}
//
//private fun getMapProperties(context: Context, isDarkTheme: Boolean): MapProperties {
//    val mapStyle = if (isDarkTheme) {
//        MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style_dark)
//    } else {
//        null
//    }
//    return MapProperties(isMyLocationEnabled = false, mapStyleOptions = mapStyle)
//}
//
//@Preview
//@Composable
//private fun MapScreenPreview() {
//    MapPDPTheme {
//        MapContent(
//            mapProperties = MapProperties(),
//            uiSettings = MapUiSettings(),
//            cameraPositionState = rememberCameraPositionState(),
//            savedLocations = listOf(),
//            onMapLongClick = {},
//            onMarkerClick = {},
//        )
//    }
//}