package com.example.trackwater.domain

import androidx.lifecycle.LiveData

interface WaterNormaRepository {

    fun getWaterNorma(waterNormaID: Int): WaterNorma

    fun initWaterNorma(waterNorma: WaterNorma)

    fun editWaterNorma(waterNorma: WaterNorma)

    fun addWaterNorma(waterNorma: WaterNorma, drinking: Int)
}