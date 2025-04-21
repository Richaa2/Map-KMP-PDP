package com.richaa2.map.kmp.data.source

import com.richaa2.map.kmp.data.source.remote.model.LatLngWrapper
import com.richaa2.map.kmp.data.source.remote.model.RouteResponse

interface RemoteSource {

    suspend fun computeRoute(
        origin: LatLngWrapper,
        destination: LatLngWrapper,
    ): RouteResponse
}