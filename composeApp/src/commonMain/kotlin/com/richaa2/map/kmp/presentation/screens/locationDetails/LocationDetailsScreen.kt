package com.richaa2.map.kmp.presentation.screens.locationDetails//package com.richaa2.mappdp.presentation.locationDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.presentation.screens.addLocation.components.toImageBitmap
import com.richaa2.map.kmp.presentation.screens.locationDetails.components.ConfirmationDialog
import com.richaa2.map.kmp.presentation.utils.LocalErrorFlow
import com.richaa2.mappdp.designsystem.components.LoadingContent
import com.richaa2.mappdp.designsystem.components.NotFoundContent
import mapkmp.composeapp.generated.resources.Res
import mapkmp.composeapp.generated.resources.are_you_sure_you_want_to_delete_this_location
import mapkmp.composeapp.generated.resources.back
import mapkmp.composeapp.generated.resources.confirm_delete
import mapkmp.composeapp.generated.resources.delete_location
import mapkmp.composeapp.generated.resources.description_empty
import mapkmp.composeapp.generated.resources.edit_location
import mapkmp.composeapp.generated.resources.location_details
import mapkmp.composeapp.generated.resources.no_image_for_this_location
import mapkmp.composeapp.generated.resources.route_to_location
import mapkmp.composeapp.generated.resources.selected_image
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun LocationDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: LocationDetailsViewModel = koinViewModel<LocationDetailsViewModel>(),
    locationId: Long,
    userPosition: LatLong?,
    onBack: () -> Unit,
    onNavToRoute: (LatLong, LatLong) -> Unit,
    onEdit: (LatLong, Long) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    val errorFlow = LocalErrorFlow.current

    LaunchedEffect(key1 = locationId) {
        viewModel.loadLocationDetails(locationId, userPosition)
    }

    LaunchedEffect(Unit) {
        viewModel.errorAction.collect { errorMessage ->
            errorFlow.emit(errorMessage)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onNavigateBackAction.collect { shouldNavigateBack ->
            if (shouldNavigateBack) {
                onBack()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onNavigateToRouteAction.collect { destinationPosition ->
            onNavToRoute(userPosition!!, destinationPosition)
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(Res.string.location_details)) },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.back)
                        )
                    }
                },
                actions = {
                    uiState.let {
                        if (uiState is LocationDetailsViewModel.LocationDetailsState.Success) {
                            IconButton(onClick = {
                                showDeleteDialog = true
                            }) {
                                Icon(
                                    Icons.Filled.Delete,
                                    contentDescription = stringResource(Res.string.delete_location)
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            uiState.let {
                if (it is LocationDetailsViewModel.LocationDetailsState.Success) {
                    FloatingActionButton(
                        modifier = Modifier,
                        onClick = {
                            onEdit(
                                LatLong(
                                    it.location.latitude,
                                    it.location.longitude
                                ), locationId
                            )
                        },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = stringResource(Res.string.edit_location)
                        )
                    }
                }
            }

        },
    ) { innerPadding ->
        when (val state = uiState) {
            is LocationDetailsViewModel.LocationDetailsState.Success -> {
                LocationDetailContent(
                    modifier = Modifier.padding(innerPadding),
                    location = state.location,
                    userPosition = userPosition,
                    onNavToRoute = {
                        viewModel.getRouteToLocation()
                    },
                    )
            }

            is LocationDetailsViewModel.LocationDetailsState.Loading -> {
                LoadingContent(innerPadding)
            }

            is LocationDetailsViewModel.LocationDetailsState.NotFound -> {
                NotFoundContent()
            }
        }
        if (showDeleteDialog) {
            ConfirmationDialog(
                title = stringResource(Res.string.confirm_delete),
                message = stringResource(Res.string.are_you_sure_you_want_to_delete_this_location),
                onConfirm = {
                    viewModel.deleteLocation(locationId)
                    showDeleteDialog = false
                },
                onDismiss = { showDeleteDialog = false }
            )
        }
    }

}


@Composable
fun LocationDetailContent(
    modifier: Modifier = Modifier,
    location: LocationInfo,
    userPosition: LatLong?,
    onNavToRoute: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentAlignment = Alignment.Center
        ) {

            if (location.imageUrl != null) {
                Image(
                    bitmap = location.imageUrl.toImageBitmap(),
                    contentDescription = stringResource(Res.string.selected_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.small)
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            MaterialTheme.colorScheme.surfaceContainerHighest,
                            shape = MaterialTheme.shapes.small
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.no_image_for_this_location),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = location.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = location.description ?: stringResource(Res.string.description_empty),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = if (userPosition != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            onClick = {
                onNavToRoute()
            },
            modifier = Modifier.fillMaxWidth(),
            content = {
                Text(
                    text = stringResource(Res.string.route_to_location),
                    style = MaterialTheme.typography.bodyLarge,

                    )
            }
        )

    }
}