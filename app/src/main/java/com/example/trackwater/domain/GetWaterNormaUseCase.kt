package com.example.trackwater.domain

import androidx.lifecycle.LiveData

class GetWaterNormaUseCase(private val waterNormaRepository: WaterNormaRepository) {

    fun getWaterNorma(): WaterNorma {
        return waterNormaRepository.getWaterNorma()
    }
}