package com.example.trackwater.domain

import androidx.lifecycle.LiveData

class GetWaterNormaUseCase(private val waterNormaRepository: WaterNormaRepository) {

    fun getWaterNorma(waterNormaID: Int = -1): WaterNorma {
        return waterNormaRepository.getWaterNorma(waterNormaID)
    }
}