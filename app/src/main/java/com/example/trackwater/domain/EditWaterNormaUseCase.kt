package com.example.trackwater.domain

class EditWaterNormaUseCase(private val waterNormaRepository: WaterNormaRepository) {
    fun editWaterNorma(waterNorma: WaterNorma) {
        waterNormaRepository.editWaterNorma(waterNorma)
    }

}