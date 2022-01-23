package com.example.trackwater.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trackwater.domain.Setting
import com.example.trackwater.domain.WaterNorma
import com.example.trackwater.domain.WaterNormaRepository
import java.lang.RuntimeException

object WaterNormaRepositoryImpl: WaterNormaRepository {

    private val waterNormas = mutableListOf<WaterNorma>()

    init {
        val water = WaterNorma(2890, 200)
        waterNormas.add(water)
    }

    override fun getWaterNorma(waterNormaID: Int): WaterNorma {
        return waterNormas.find {
            it.id == waterNormaID
        } ?: throw RuntimeException("Setting $waterNormaID not found")
    }

    override fun initWaterNorma(waterNorma: WaterNorma) {
        waterNormas.add(waterNorma)
    }

    override fun editWaterNorma(waterNorma: WaterNorma) {
        val oldWaterNorma = getWaterNorma(waterNorma.id)
        waterNormas.remove(oldWaterNorma)
        initWaterNorma(waterNorma)
    }

    override fun addWaterNorma(waterNorma: WaterNorma, drinking: Int) {
        var drink = waterNorma.copy(drink = waterNorma.drink + drinking)
        editWaterNorma(waterNorma)
    }

}