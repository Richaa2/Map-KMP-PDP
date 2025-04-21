package com.richaa2.map.kmp.domain.repository

import com.richaa2.map.kmp.domain.model.CameraPosition

interface CameraRepository {
   suspend fun getLastCameraPosition(): CameraPosition

   suspend fun insertCameraPosition(cameraPosition: CameraPosition)
}