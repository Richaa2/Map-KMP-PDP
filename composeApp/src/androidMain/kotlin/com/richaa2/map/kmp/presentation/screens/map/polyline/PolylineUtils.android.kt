package com.richaa2.map.kmp.presentation.screens.map.polyline

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import com.richaa2.map.kmp.presentation.screens.map.camera.NativeMap
import dev.icerock.moko.geo.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual object PolylineUtils {
    actual suspend fun decode(encodedPath: String): List<LatLng> {
        return withContext(Dispatchers.Default) {
            PolyUtil.decode(encodedPath).map { latLng ->
                LatLng(latLng.latitude, latLng.longitude)
            }
        }
    }

    actual fun drawPolyline(coordinates: List<LatLng>, map: NativeMap) {
        val gmsCoordinates =
            coordinates.map { com.google.android.gms.maps.model.LatLng(it.latitude, it.longitude) }
        val polylineOptions = PolylineOptions()
            .addAll(gmsCoordinates)
            .color(Color.Blue.toArgb())
            .width(5f)
        map.addPolyline(polylineOptions)
    }

    actual fun fitCameraToPolyline(polylineCoordinates: List<LatLng>, nativeMap: NativeMap) {
        val gmsCoordinates = polylineCoordinates.map {
            com.google.android.gms.maps.model.LatLng(
                it.latitude,
                it.longitude
            )
        }

        val bounds = LatLngBounds.builder().apply {
            gmsCoordinates.forEach {
                include(
                    com.google.android.gms.maps.model.LatLng(
                        it.latitude,
                        it.longitude
                    )
                )
            }
        }.build()

        val padding = 100

        nativeMap.setLatLngBoundsForCameraTarget(bounds)
        nativeMap.moveCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                padding
            )
        )

    }
}