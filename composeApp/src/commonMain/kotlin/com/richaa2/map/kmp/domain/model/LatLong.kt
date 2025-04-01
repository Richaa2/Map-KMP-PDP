package com.richaa2.map.kmp.domain.model


data class LatLong(val latitude: Double = 0.0, val longitude: Double = 0.0) {

    companion object {
        fun build(latitude: Float?, longitude: Float?): LatLong? {
            if (latitude == null || longitude == null) return null
            return LatLong(latitude = latitude.toDouble(), longitude = longitude.toDouble())
        }
    }
}