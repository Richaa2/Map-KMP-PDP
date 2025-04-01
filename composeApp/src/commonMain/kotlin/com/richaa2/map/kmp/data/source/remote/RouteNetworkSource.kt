package com.richaa2.map.kmp.data.source.remote

import com.richaa2.map.kmp.data.source.remote.model.ComputeRoutesRequest
import com.richaa2.map.kmp.data.source.remote.model.LatLngWrapper
import com.richaa2.map.kmp.data.source.remote.model.RouteResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

class RouteNetworkSource(private val client: HttpClient) {

    suspend fun computeRoute(
        origin: LatLngWrapper,
        destination: LatLngWrapper,
    ): RouteResponse {
        val requestBody = ComputeRoutesRequest.getDefaultRouteRequest(
            origin = origin, destination = destination
        )

        return client.post("https://routes.googleapis.com/directions/v2:computeRoutes") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header("X-Goog-FieldMask", "routes.distanceMeters,routes.polyline.encodedPolyline")
            setBody(requestBody)
        }.body()
    }
}