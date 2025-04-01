package com.richaa2.map.kmp.data.mapper

import com.richaa2.map.kmp.data.source.remote.model.LatLngWrapper
import com.richaa2.map.kmp.data.source.remote.model.RouteData
import com.richaa2.map.kmp.domain.model.ComputeRoute
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.presentation.map.polyline.PolylineUtils

object RoutesMapper {

    fun LatLong.fromDomainToData(): LatLngWrapper {
        return LatLngWrapper(this.latitude, this.longitude)
    }

   suspend fun RouteData.fromDataToDomain(): ComputeRoute {
        return ComputeRoute(
            routePolyline = PolylineUtils.decode(this.polyline.encodedPolyline),
            distanceMeters =  this.distanceMeters
        )
    }
}