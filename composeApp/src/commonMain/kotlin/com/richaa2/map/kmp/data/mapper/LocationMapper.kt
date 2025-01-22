package com.richaa2.mappdp.data.mapper

import com.richaa2.mappdp.data.source.local.LocationInfoEntity
import com.richaa2.mappdp.domain.model.LocationInfo


class LocationMapper  constructor(){

    fun fromEntityToDomain(entity: LocationInfoEntity): LocationInfo {
        return LocationInfo(
            id = entity.id,
            latitude = entity.latitude,
            longitude = entity.longitude,
            title = entity.title,
            description = entity.description,
            imageUrl = entity.imageUrl,
        )
    }

    fun fromDomainToEntity(domain: LocationInfo): LocationInfoEntity {
        return LocationInfoEntity(
            id = domain.id,
            latitude = domain.latitude,
            longitude = domain.longitude,
            title = domain.title,
            description = domain.description,
            imageUrl = domain.imageUrl,
        )
    }
}