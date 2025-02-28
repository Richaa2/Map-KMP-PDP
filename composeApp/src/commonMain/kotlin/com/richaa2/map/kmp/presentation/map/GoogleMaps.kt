package com.richaa2.map.kmp.presentation.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.model.LocationInfo

@Composable
expect fun GoogleMaps(
    modifier: Modifier,
    savedLocations: List<LocationInfo>,
    onMarkerClick: (LocationInfo) -> Unit,
    onMapLongClick: (LatLong) -> Unit,
)