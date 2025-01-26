package com.richaa2.map.kmp.presentation.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.compose.GoogleMap
import com.richaa2.map.kmp.domain.model.LatLong

@Composable
actual fun GoogleMaps(
    modifier: Modifier,
    onMapClick: (LatLong) -> Unit,
    onMapLongClick: (LatLong) -> Unit,
) {
    GoogleMap(
        modifier = modifier,
        onMapClick = { latlng ->
            onMapClick(
                LatLong(
                    latlng.latitude,
                    latlng.longitude
                )
            )
        },
        onMapLongClick = { latlng ->
            onMapClick(
                LatLong(
                    latlng.latitude,
                    latlng.longitude
                )
            )
        },
    ) {

    }
}