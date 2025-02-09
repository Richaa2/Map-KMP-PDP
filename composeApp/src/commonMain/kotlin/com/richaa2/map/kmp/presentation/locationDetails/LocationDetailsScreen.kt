package com.richaa2.map.kmp.presentation.locationDetails//package com.richaa2.mappdp.presentation.locationDetails
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.Scaffold
//import androidx.compose.material.SnackbarHost
//import androidx.compose.material.SnackbarHostState
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.Delete
//import androidx.compose.material.icons.filled.Edit
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
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
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import coil.compose.rememberAsyncImagePainter
//import com.google.android.gms.maps.model.LatLng
//import com.richaa2.mappdp.R
//import com.richaa2.mappdp.designsystem.components.LoadingContent
//import com.richaa2.mappdp.designsystem.components.NotFoundContent
//import com.richaa2.mappdp.designsystem.theme.MapPDPTheme
//import com.richaa2.map.kmp.domain.model.LocationInfo
//import com.richaa2.map.kmp.presentation.locationDetails.components.ConfirmationDialog
//import com.richaa2.mappdp.utils.base64ToBitmap
//import kotlinx.coroutines.flow.collectLatest
//import org.koin.compose.viewmodel.koinViewModel
//
//@Composable
//fun LocationDetailsScreen(
//    modifier: Modifier = Modifier,
//    viewModel: LocationDetailsViewModel = koinViewModel<LocationDetailsViewModel>(),
//    locationId: Long,
//    onBack: () -> Unit,
//    onEdit: (LatLng, Long) -> Unit,
//) {
//    val uiState by viewModel.uiState.collectAsState()
//    val errorMessage by viewModel.errorState.collectAsState()
//    var showDeleteDialog by remember { mutableStateOf(false) }
//    val snackBarHostState = remember { SnackbarHostState() }
//
//    LaunchedEffect(key1 = locationId) {
//        viewModel.loadLocationDetails(locationId)
//    }
//
//    LaunchedEffect(errorMessage) {
//        errorMessage?.let {
//            snackBarHostState.showSnackbar(it)
//            viewModel.clearErrorMessage()
//        }
//    }
//    LaunchedEffect(Unit) {
//        viewModel.onNavigateBackAction.collectLatest { shouldNavigateBack ->
//            if (shouldNavigateBack) {
//                onBack()
//            }
//        }
//    }
//
//    Scaffold(
//        modifier = modifier,
//        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = { Text(stringResource(R.string.location_details)) },
//                navigationIcon = {
//                    IconButton(onClick = { onBack() }) {
//                        Icon(
//                            Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = stringResource(
//                                R.string.back
//                            )
//                        )
//                    }
//                },
//                actions = {
//                    uiState.let {
//                        if (uiState is LocationDetailsViewModel.LocationDetailsState.Success) {
//                            IconButton(onClick = {
//                                showDeleteDialog = true
//                            }) {
//                                Icon(
//                                    Icons.Filled.Delete,
//                                    contentDescription = stringResource(R.string.delete_location)
//                                )
//                            }
//                        }
//                    }
//                },
//                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primary,
//                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
//                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
//                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
//                )
//            )
//        },
//        floatingActionButton = {
//            uiState.let {
//                if (it is LocationDetailsViewModel.LocationDetailsState.Success) {
//                    FloatingActionButton(
//                        onClick = {
//                            onEdit(
//                                LatLng(
//                                    it.location.latitude,
//                                    it.location.longitude
//                                ), locationId
//                            )
//                        },
//                        containerColor = MaterialTheme.colorScheme.primary
//                    ) {
//                        Icon(
//                            Icons.Filled.Edit,
//                            contentDescription = stringResource(R.string.edit_location)
//                        )
//                    }
//                }
//            }
//
//        },
//    ) { innerPadding ->
//        when (val state = uiState) {
//            is LocationDetailsViewModel.LocationDetailsState.Success -> {
//                LocationDetailContent(
//                    modifier = Modifier.padding(innerPadding),
//                    location = state.location
//                )
//            }
//
//            is LocationDetailsViewModel.LocationDetailsState.Loading -> {
//                LoadingContent(innerPadding)
//            }
//
//            is LocationDetailsViewModel.LocationDetailsState.NotFound -> {
//                NotFoundContent()
//            }
//        }
//        if (showDeleteDialog) {
//            ConfirmationDialog(
//                title = stringResource(R.string.confirm_delete),
//                message = stringResource(R.string.are_you_sure_you_want_to_delete_this_location),
//                onConfirm = {
//                    viewModel.deleteLocation(locationId)
//                    showDeleteDialog = false
//                },
//                onDismiss = { showDeleteDialog = false }
//            )
//        }
//    }
//
//}
//
//
//@Composable
//fun LocationDetailContent(
//    modifier: Modifier = Modifier,
//    location: LocationInfo,
//) {
//    Column(
//        modifier = modifier
//            .padding(16.dp)
//            .verticalScroll(rememberScrollState()),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.Start
//    ) {
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(400.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            if (location.imageUrl?.base64ToBitmap() != null) {
//                Image(
//                    painter = rememberAsyncImagePainter(location.imageUrl.base64ToBitmap()),
//                    contentDescription = stringResource(R.string.selected_image),
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .clip(MaterialTheme.shapes.small)
//                )
//            } else {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(
//                            MaterialTheme.colorScheme.surfaceContainerHighest,
//                            shape = MaterialTheme.shapes.small
//                        ),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = stringResource(R.string.no_image_for_this_location),
//                        color = MaterialTheme.colorScheme.onSurface
//                    )
//                }
//            }
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(
//            text = location.title,
//            style = MaterialTheme.typography.titleLarge,
//            color = MaterialTheme.colorScheme.onBackground,
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(
//            text = location.description ?: stringResource(R.string.description_empty),
//            style = MaterialTheme.typography.bodyMedium,
//            color = MaterialTheme.colorScheme.onBackground
//        )
//
//    }
//}
//
//
//@Preview(showBackground = true)
//@Composable
//private fun LocationDetailsPreview() {
//    MapPDPTheme {
//        LocationDetailContent(
//            location = LocationInfo(
//                id = 1,
//                title = "Lviv",
//                description = "Lviv is a city in Ukraine, the administrative center of the region, agglomeration, district, urban community, the national-cultural and educational-scientific center of the country, a large industrial center and transport hub, it is considered the capital of Galicia and the center of Western of Ukraine. It is the fifth largest city in the country in terms of population (717,273 as of January 1, 2022)[2].\n" +
//                        "\n" +
//                        "Lviv was founded by King Danylo around 1231-1235 (the first mention is from 1256). Around 1272, the city became the capital of the Kingdom of Rus (Halytskyi-Volyn Principality). Shortly after the death of Prince Yuri II, Lviv came under the rule of the Kingdom of Poland for more than four centuries. In 1356, the city received Magdeburg rights; During the Middle Ages, Lviv was an important trade center. During Austrian rule, the city became a center of Ukrainian and Polish national movements. After the collapse of Austria-Hungary in the fall of 1918, Lviv was for some time the capital of the West Ukrainian People's Republic, but after the Ukrainian-Polish battle for the city in November 1918, it passed to Poland, which was recognized by international pacts and agreements in 1922-1923. During the Second World War, the city was captured by the Soviet Union, and later by Germany. After the war, the Yalta Agreement of 1945 was legally enshrined, according to which Eastern Galicia and in particular Lviv remained part of the Ukrainian SSR. In 1946, a population exchange took place between Poland and the Ukrainian SSR, which, together with the consequences of the war, significantly affected the population of Lviv. Since 1991, Lviv has been part of Ukraine.\n" +
//                        "\n",
//                latitude = 12.0,
//                longitude = 12.0,
//                imageUrl = null,
//            )
//        )
//    }
//}
