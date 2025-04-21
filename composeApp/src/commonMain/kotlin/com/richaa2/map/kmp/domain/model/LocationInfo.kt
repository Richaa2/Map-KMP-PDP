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

        if (id != other.id) return false
        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (imageUrl != null) {
            if (other.imageUrl == null) return false
            if (!imageUrl.contentEquals(other.imageUrl)) return false
        } else if (other.imageUrl != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (imageUrl?.contentHashCode() ?: 0)
        return result
    }



}