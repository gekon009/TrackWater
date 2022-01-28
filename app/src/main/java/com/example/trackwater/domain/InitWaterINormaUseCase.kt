package com.example.trackwater.domain

class InitWaterNormaUseCase(private val waterNormaRepository: WaterNormaRepository) {

    fun initWaterNorma(waterNorma: WaterNorma) {
        waterNormaRepository.initWaterNorma(waterNorma)
    }
}