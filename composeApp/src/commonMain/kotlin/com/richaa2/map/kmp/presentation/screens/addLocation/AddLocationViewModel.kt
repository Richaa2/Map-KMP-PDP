package com.richaa2.map.kmp.presentation.screens.addLocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.richaa2.map.kmp.domain.common.Resource
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.domain.usecase.locationInfo.GetLocationInfoUseCase
import com.richaa2.map.kmp.domain.usecase.locationInfo.SaveLocationInfoUseCase
import com.richaa2.map.kmp.domain.usecase.locationInfo.UpdateLocationInfoUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mapkmp.composeapp.generated.resources.Res
import mapkmp.composeapp.generated.resources.title_cannot_be_empty
import org.jetbrains.compose.resources.getString

class AddLocationViewModel(
    private val saveLocationInfoUseCase: SaveLocationInfoUseCase,
    private val updateLocationInfoUseCase: UpdateLocationInfoUseCase,
    private val getLocationInfoUseCase: GetLocationInfoUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddLocationState>(AddLocationState.Loading)
    val uiState: StateFlow<AddLocationState> = _uiState.asStateFlow()

    private val _errorAction = MutableSharedFlow<String>(replay = 0)
    val errorAction: SharedFlow<String> = _errorAction.asSharedFlow()

    private val _formState = MutableStateFlow(AddLocationFormState())
    val formState: StateFlow<AddLocationFormState> = _formState.asStateFlow()

    private var editLocation: LocationInfo? = null

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent.asSharedFlow()

    fun initLocationInfo(locationId: Long?) {
        viewModelScope.launch {
            locationId?.let {
                getLocationInfoUseCase(locationId).let { result ->
                    when (result) {
                        is Resource.Error -> {
                            _errorAction.emit(result.message)
                        }

                        Resource.Loading -> {
                            _uiState.value = AddLocationState.Loading
                        }

                        is Resource.Success -> {
                            val data = result.data
                            editLocation = data
                            _formState.value = AddLocationFormState(
                                title = data?.title ?: "",
                                description = data?.description ?: "",
                                image = data?.imageUrl
                            )
                            _uiState.value = AddLocationState.Success
                        }
                    }
                }
            } ?: run {
                editLocation = null
                _uiState.value = AddLocationState.Success
            }
        }
    }


    fun onTitleChange(newTitle: String) {
        _formState.value = _formState.value.copy(title = newTitle)
    }

    fun onDescriptionChange(newDescription: String) {
        _formState.value = _formState.value.copy(description = newDescription)
    }

    fun onImageSelected(imageByteArray: ByteArray?) {
        _formState.value = _formState.value.copy(image = imageByteArray)
    }

    fun onRemoveSelectedImage() {
        _formState.value = _formState.value.copy(image = null)
    }

    fun saveLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            if (_uiState.value is AddLocationState.Loading) return@launch
            val title = _formState.value.title.trim()
            val description = _formState.value.description.trim()

            if (title.isEmpty()) {
                _formState.value =
                    _formState.value.copy(titleError = getString(Res.string.title_cannot_be_empty))
                return@launch
            } else {
                _formState.value = _formState.value.copy(titleError = null)
            }
            _uiState.value = AddLocationState.Loading

            viewModelScope.launch {
                val result = editLocation?.let {
                    updateLocationInfoUseCase(
                        it.copy(
                            title = title,
                            description = description,
                            imageUrl = _formState.value.image
                        )
                    )
                } ?: run {
                    saveLocationInfoUseCase(
                        LocationInfo(
                            title = title,
                            description = description,
                            latitude = latitude,
                            longitude = longitude,
                            imageUrl = _formState.value.image,
                        )
                    )
                }

                when (result) {
                    is Resource.Success -> {
                        onNavigateBack()
                    }

                    is Resource.Error -> {
                        _errorAction.emit(result.message)
                        _uiState.value = AddLocationState.Success
                    }

                    Resource.Loading -> Unit
                }
            }
        }

    }

    fun onNavigateBack() {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.NavigateBack)
        }
    }

    sealed class AddLocationState {
        data object Loading : AddLocationState()
        data object Success : AddLocationState()
    }
    sealed class NavigationEvent {
        data object NavigateBack : NavigationEvent()
    }
}
