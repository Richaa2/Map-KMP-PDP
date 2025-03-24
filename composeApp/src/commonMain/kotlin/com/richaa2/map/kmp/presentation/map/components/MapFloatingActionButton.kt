package com.richaa2.map.kmp.presentation.map.components

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.presentation.map.camera.CameraPositionState
import com.richaa2.map.kmp.presentation.map.utils.ANIMATION_TO_CURRENT_LOCATION_DURATION_MS
import com.richaa2.map.kmp.presentation.map.utils.DEFAULT_ZOOM_LEVEL
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
        modifier = modifier.navigationBarsPadding(),
        containerColor = buttonColor,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = stringResource(Res.string.current_location)
        )
    }
}

//
//@Preview()
//@Composable
//fun MapFloatingActionButtonPreview() {
//    val testLocation = Location("").apply {
//        latitude = 50.4501
//        longitude = 30.5234
//    }
//
//    val cameraPositionState = rememberCameraPositionState()
//    MapFloatingActionButton(
//        cameraPositionState = cameraPositionState,
//        currentLocation = testLocation,
//        modifier = Modifier.padding(16.dp),
//        onDisabledClick = {}
//
//    )
//}
//
//@Preview()
//@Composable
//fun MapFloatingActionButtonDisabledPreview() {
//
//    val cameraPositionState = rememberCameraPositionState()
//
//    MapFloatingActionButton(
//        cameraPositionState = cameraPositionState,
//        currentLocation = null,
//        modifier = Modifier.padding(16.dp),
//        onDisabledClick = {}
//
//    )
//}