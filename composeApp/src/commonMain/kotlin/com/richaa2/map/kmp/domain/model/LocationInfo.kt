package com.richaa2.map.kmp.domain.model

data class LocationInfo(
    val id: Long? = null,
    val latitude: Double,
    val longitude: Double,
    val title: String,
    val description: String?,
    val imageUrl: String?,
)