package com.example.trackwater.domain

class AddWaterNormaUseCase(private val waterNormaRepository: WaterNormaRepository) {

    fun addWaterNorma(waterNorma: WaterNorma, drinking: Int) {
        waterNormaRepository.addWaterNorma(waterNorma, drinking)

    }
}