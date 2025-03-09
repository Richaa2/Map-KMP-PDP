package com.richaa2.map.kmp.data.mapper

import com.richaa2.db.Locations_info
import com.richaa2.map.kmp.domain.model.LocationInfo


object LocationMapper {

    fun Locations_info.fromEntityToDomain(): LocationInfo {
        return LocationInfo(
            id = this.id,
            latitude = this.latitude,
            longitude = this.longitude,
            title = this.title,
            description = this.description,
            imageUrl = this.imageUrl,
        )
    }

}