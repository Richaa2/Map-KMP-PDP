package com.richaa2.map.kmp.domain.usecase.cameraPosition

import com.richaa2.map.kmp.domain.model.CameraPosition
import com.richaa2.map.kmp.domain.repository.CameraRepository

class GetCameraPositionUseCase (
    private val cameraRepository: CameraRepository
){

     suspend operator fun invoke(): CameraPosition {
        return cameraRepository.getLastCameraPosition()
    }
}