package com.example.trackwater.domain

interface WaterNormaRepository {

    fun getWaterNorma(): WaterNorma

    fun initWaterNorma(waterNorma: WaterNorma)

    fun drinkWaterNorma(waterNorma: WaterNorma, drinking: Int)
}