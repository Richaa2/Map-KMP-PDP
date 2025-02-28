package com.richaa2.map.kmp.presentation.map.clustering

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering
import com.richaa2.map.kmp.domain.model.LocationInfo
import com.richaa2.map.kmp.presentation.map.utils.CLUSTER_ANIMATION_ZOOM_DURATION_MS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun LocationClustering(
    clusterItems: List<LocationClusterItem>,
    cameraPositionState: CameraPositionState,
    onMarkerClick: (LocationInfo) -> Unit,
) {
    val scope = rememberCoroutineScope()
    Clustering(
        items = clusterItems,
        onClusterItemClick = { clusterItem ->
            onMarkerClick(clusterItem.locationInfo)
            true
        },
        clusterContent = {
            ClusterContent(text = "%,d".format(it.size))
        },
        onClusterClick = { cluster ->
            handleClusterClick(
                cluster = cluster,
                cameraPositionState = cameraPositionState,
                coroutineScope = scope
            )
        },
    )
}

private fun handleClusterClick(
    cluster: Cluster<LocationClusterItem>,
    cameraPositionState: CameraPositionState,
    coroutineScope: CoroutineScope,
): Boolean {
    val boundsPadding = 100
    val latLngBounds = LatLngBounds.builder().apply {
        cluster.items.forEach { item -> include(item.position) }
    }.build()

    coroutineScope.launch {
        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngBounds(latLngBounds, boundsPadding),
            durationMs = CLUSTER_ANIMATION_ZOOM_DURATION_MS
        )
    }
    return true
}

@Composable
fun ClusterContent(
    text: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier.size(40.dp),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}