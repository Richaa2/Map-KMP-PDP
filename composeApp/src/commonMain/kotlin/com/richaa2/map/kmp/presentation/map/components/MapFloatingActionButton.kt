//package com.richaa2.mappdp.presentation.map.components
//
//import android.location.Location
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.MyLocation
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.model.LatLng
//import com.google.maps.android.compose.CameraPositionState
//import com.google.maps.android.compose.rememberCameraPositionState
//import com.richaa2.mappdp.R
//import kotlinx.coroutines.launch
//
//@Composable
//fun MapFloatingActionButton(
//    modifier: Modifier = Modifier,
//    cameraPositionState: CameraPositionState,
//    currentLocation: Location?,
//    onDisabledClick: () -> Unit,
//) {
//    val coroutineScope = rememberCoroutineScope()
//    val isLocationAvailable = currentLocation != null
//    val buttonColor = if (isLocationAvailable) {
//        MaterialTheme.colorScheme.primary
//    } else {
//        MaterialTheme.colorScheme.outline
//    }
//
//    FloatingActionButton(
//        onClick = {
//            coroutineScope.launch {
//                if (isLocationAvailable) {
//                    cameraPositionState.animate(
//                        update = CameraUpdateFactory.newLatLngZoom(
//                            LatLng(currentLocation!!.latitude, currentLocation.longitude),
//                            15f
//                        ),
//                        durationMs = 1000
//                    )
//                } else {
//                    onDisabledClick()
//                }
//            }
//        },
//        modifier = modifier,
//        containerColor = buttonColor,
//        contentColor = MaterialTheme.colorScheme.onPrimary
//    ) {
//        Icon(
//            imageVector = Icons.Filled.MyLocation,
//            contentDescription = stringResource(R.string.current_location)
//        )
//    }
//}
//
//
//@Preview(showBackground = true)
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
//@Preview(showBackground = true)
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