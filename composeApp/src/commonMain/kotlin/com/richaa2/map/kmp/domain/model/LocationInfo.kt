package com.richaa2.map.kmp.domain.model

data class LocationInfo(
    val id: Long = 0,
    val latitude: Double,
    val longitude: Double,
    val title: String,
    val description: String?,
    val imageUrl: ByteArray?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as LocationInfo

        if (imageUrl != null) {
            if (other.imageUrl == null) return false
            if (!imageUrl.contentEquals(other.imageUrl)) return false
        } else if (other.imageUrl != null) return false

        return true
    }

    override fun hashCode(): Int {
        return imageUrl?.contentHashCode() ?: 0
    }
}