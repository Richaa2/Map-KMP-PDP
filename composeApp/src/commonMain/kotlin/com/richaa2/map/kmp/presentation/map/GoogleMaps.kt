package com.richaa2.map.kmp.presentation.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.richaa2.map.kmp.domain.model.LatLong

@Composable
expect fun GoogleMaps(
    modifier: Modifier,

    onMapClick: (LatLong) -> Unit,
    onMapLongClick: (LatLong) -> Unit,
)