package com.example.trackwater.domain

import androidx.lifecycle.LiveData

interface WaterNormaRepository {

    fun getWaterNorma(): LiveData<WaterNorma>

    fun initWaterNorma(waterNorma: WaterNorma)

    fun drinkWaterNorma(waterNorma: WaterNorma, drinking: Int)
}