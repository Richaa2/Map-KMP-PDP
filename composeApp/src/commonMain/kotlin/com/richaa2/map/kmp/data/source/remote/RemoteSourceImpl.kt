package com.richaa2.map.kmp.data.source.remote

import com.richaa2.map.kmp.data.source.RemoteSource
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

class RemoteSourceImpl(private val client: HttpClient): RemoteSource {

    override suspend fun computeRoute(
        origin: LatLngWrapper,
        destination: LatLngWrapper,
    ): RouteResponse {
        val requestBody = ComputeRoutesRequest.getDefaultRouteRequest(
            origin = origin, destination = destination
        )

        return client.post(BASE_URL) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header(HEADER_FIELD_MASK_KEY, HEADER_FIELD_MASK_VALUE)
            setBody(requestBody)
        }.body()
    }

    companion object {
        private const val BASE_URL = "https://routes.googleapis.com/directions/v2:computeRoutes"
        private const val HEADER_FIELD_MASK_KEY = "X-Goog-FieldMask"
        private const val HEADER_FIELD_MASK_VALUE = "routes.distanceMeters,routes.polyline.encodedPolyline"
    }
}