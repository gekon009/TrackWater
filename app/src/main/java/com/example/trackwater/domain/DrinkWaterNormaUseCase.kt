package com.example.trackwater.domain

class DrinkWaterNormaUseCase(private val waterNormaRepository: WaterNormaRepository) {

    fun drinkWaterNorma(waterNorma: WaterNorma, drinking: Int) {
        waterNormaRepository.drinkWaterNorma(waterNorma, drinking)
    }
}