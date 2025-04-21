package com.richaa2.map.kmp.domain.usecase.cameraPosition

import com.richaa2.map.kmp.domain.model.CameraPosition
import com.richaa2.map.kmp.domain.model.LatLong
import com.richaa2.map.kmp.domain.repository.CameraRepository

class SaveCameraPositionUseCase(
    private val cameraRepository: CameraRepository
) {

    suspend operator fun invoke(
        latLong: LatLong,
        zoom: Float,
    ) {
        cameraRepository.insertCameraPosition(
            CameraPosition(
                target = latLong,
                zoom = zoom
            )
        )
    }
}