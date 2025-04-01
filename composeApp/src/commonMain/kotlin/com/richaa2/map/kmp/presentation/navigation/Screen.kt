package com.richaa2.mappdp.navigation

import kotlinx.serialization.Serializable


@Serializable
sealed class Screen {

    @Serializable
    data class Map(
        val userPositionLatitude: Float? = null,
        val userPositionLongitude: Float? = null,
        val destinationPositionLatitude: Float? = null,
        val destinationPositionLongitude: Float? = null,
    ) : Screen()


    @Serializable
    data class AddLocation(
        val latitude: Float,
        val longitude: Float,
        val locationId: Long? = null
    ) : Screen()

    @Serializable
    data class LocationDetails(
        val locationId: Long,
        val userPositionLatitude: Float?,
        val userPositionLongitude: Float?,
    ) : Screen()

}
