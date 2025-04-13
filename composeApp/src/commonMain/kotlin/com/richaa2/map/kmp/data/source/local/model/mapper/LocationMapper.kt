package com.richaa2.map.kmp.data.source.local.model.mapper

import com.richaa2.db.Locations_info
import com.richaa2.map.kmp.core.base64.base64ToByteArray
import com.richaa2.map.kmp.data.source.local.model.LocalLocationInfo
import com.richaa2.map.kmp.domain.model.LocationInfo


object LocationMapper {

    fun Locations_info.fromSqldelightToLocal(): LocalLocationInfo {
        return LocalLocationInfo(
            id = this.id,
            latitude = this.latitude,
            longitude = this.longitude,
            title = this.title,
            description = this.description,
            imageUrl = this.imageUrl?.base64ToByteArray(),
        )
    }


    fun LocalLocationInfo.fromLocalToDomain(): LocationInfo {
        return LocationInfo(
            id = this.id,
            latitude = this.latitude,
            longitude = this.longitude,
            title = this.title,
            description = this.description,
            imageUrl = this.imageUrl
        )
    }

    fun LocationInfo.fromDomainToLocal(): LocalLocationInfo {
        return LocalLocationInfo(
            id = this.id,
            latitude = this.latitude,
            longitude = this.longitude,
            title = this.title,
            description = this.description,
            imageUrl = this.imageUrl
        )
    }



}