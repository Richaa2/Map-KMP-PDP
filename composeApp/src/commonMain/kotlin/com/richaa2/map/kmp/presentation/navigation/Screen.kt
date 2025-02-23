package com.richaa2.mappdp.navigation

import kotlinx.serialization.Serializable


@Serializable
sealed class Screen {

    @Serializable
    data object Map : Screen()

    @Serializable
    data class AddLocation(
        val latitude: Float,
        val longitude: Float,
        val locationId: Long? = null
    ) : Screen()

    @Serializable
    data class LocationDetails(val locationId: Long) : Screen()
}
