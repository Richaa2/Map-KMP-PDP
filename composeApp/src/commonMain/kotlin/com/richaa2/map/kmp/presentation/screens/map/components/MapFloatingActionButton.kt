package com.richaa2.map.kmp.presentation.screens.map.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.presentation.screens.map.camera.CameraPositionState
import com.richaa2.map.kmp.presentation.screens.map.utils.ANIMATION_TO_CURRENT_LOCATION_DURATION_MS
import com.richaa2.map.kmp.presentation.screens.map.utils.DEFAULT_ZOOM_LEVEL
import kotlinx.coroutines.launch
import mapkmp.composeapp.generated.resources.Res
import mapkmp.composeapp.generated.resources.current_location
import org.jetbrains.compose.resources.stringResource

@Composable
fun MapFloatingActionButton(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    currentLocation: LatLong?,
    onDisabledClick: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val isLocationAvailable = currentLocation != null
    val buttonColor = if (isLocationAvailable) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outline
    }

    FloatingActionButton(
        onClick = {
            coroutineScope.launch {
                if (isLocationAvailable) {
                    cameraPositionState.animateTo(
                        newLatitude = currentLocation!!.latitude,
                        newLongitude = currentLocation.longitude,
                        newZoom = DEFAULT_ZOOM_LEVEL,
                        durationMillis = ANIMATION_TO_CURRENT_LOCATION_DURATION_MS
                    )
                } else {
                    onDisabledClick()
                }
            }
        },
        modifier = modifier,
        containerColor = buttonColor,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = stringResource(Res.string.current_location)
        )
    }
}