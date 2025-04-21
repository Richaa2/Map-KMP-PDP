package com.richaa2.map.kmp.presentation.screens.addLocation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.richaa2.map.kmp.presentation.screens.addLocation.components.ImagePicker
import com.richaa2.map.kmp.presentation.screens.addLocation.utils.MAX_TITLE_LENGTH
import com.richaa2.map.kmp.presentation.utils.LocalErrorFlow
import com.richaa2.mappdp.designsystem.components.LoadingContent
import compose.icons.FeatherIcons
import compose.icons.feathericons.Save
import kotlinx.coroutines.flow.collectLatest
import mapkmp.composeapp.generated.resources.Res
import mapkmp.composeapp.generated.resources.add_location
import mapkmp.composeapp.generated.resources.back
import mapkmp.composeapp.generated.resources.description_optional
import mapkmp.composeapp.generated.resources.edit_location
import mapkmp.composeapp.generated.resources.save_location
import mapkmp.composeapp.generated.resources.title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun AddLocationScreen(
    modifier: Modifier = Modifier,
    viewModel: AddLocationViewModel = koinViewModel<AddLocationViewModel>(),
    latitude: Double,
    longitude: Double,
    locationId: Long?,
    onBack: () -> Unit,
) {
    val formState by viewModel.formState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val errorFlow = LocalErrorFlow.current

    LaunchedEffect(Unit) {
        viewModel.errorAction.collect {
            errorFlow.emit(it)
        }
    }
    LaunchedEffect(Unit) {
        viewModel.initLocationInfo(locationId)
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collectLatest { event ->
            when (event) {
                AddLocationViewModel.NavigationEvent.NavigateBack -> onBack()
            }
        }
    }

    Scaffold(
        modifier = modifier.imePadding(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (locationId == null) stringResource(Res.string.add_location) else stringResource(Res.string.edit_location)) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.back),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,

                    )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier,
                onClick = {
                    viewModel.saveLocation(latitude, longitude)
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(

                    imageVector = FeatherIcons.Save,
                    contentDescription = stringResource(Res.string.save_location)
                )
            }
        },
    ) { innerPadding ->
        when (uiState) {
            is AddLocationViewModel.AddLocationState.Loading -> {
                LoadingContent(innerPadding = innerPadding)
            }

            is AddLocationViewModel.AddLocationState.Success -> {
                AddLocationContent(
                    modifier = Modifier
                        .padding(innerPadding),
                    formState = formState,
                    onTitleChange = { viewModel.onTitleChange(it) },
                    onDescriptionChange = { viewModel.onDescriptionChange(it) },
                    onImageSelected = { uri -> viewModel.onImageSelected(uri) },
                    onRemoveSelectedImage = { viewModel.onRemoveSelectedImage() }
                )
            }
        }
    }
}

@Composable
fun AddLocationContent(
    modifier: Modifier = Modifier,
    formState: AddLocationFormState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onImageSelected: (ByteArray?) -> Unit,
    onRemoveSelectedImage: () -> Unit,
) {
    val bitmap = remember(formState.image) {
        formState.image
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ImagePicker(
                selectedImageBitmap = bitmap,
                onImageSelected = onImageSelected,
                onImageRemoved = onRemoveSelectedImage
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = formState.title,
                onValueChange = {
                    if (it.length <= MAX_TITLE_LENGTH) onTitleChange(it)
                },
                label = { Text(stringResource(Res.string.title)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                isError = formState.titleError != null,
                supportingText = {
                    formState.titleError?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = formState.description,
                onValueChange = onDescriptionChange,
                label = { Text(stringResource(Res.string.description_optional)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 150.dp),
                maxLines = 5,
                isError = formState.descriptionError != null,
                supportingText = {
                    formState.descriptionError?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
            )
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}
