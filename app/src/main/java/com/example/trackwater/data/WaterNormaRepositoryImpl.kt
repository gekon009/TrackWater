package com.example.trackwater.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trackwater.domain.Setting
import com.example.trackwater.domain.WaterNorma
import com.example.trackwater.domain.WaterNormaRepository
import java.lang.RuntimeException

object WaterNormaRepositoryImpl: WaterNormaRepository {

    private var waterNormasLD = MutableLiveData<WaterNorma>()
    private lateinit var waterNormas : WaterNorma

    init {
        val water = WaterNorma(2890, 200)
        initWaterNorma(water)
    }

    override fun initWaterNorma(waterNorma: WaterNorma) {
        waterNormas = WaterNorma(waterNorma.norma, waterNorma.drink)
        updateWaterNorma()
    }

    override fun getWaterNorma(): WaterNorma {
        return waterNormas
    }

    override fun drinkWaterNorma(waterNorma: WaterNorma, drinking: Int) {
        var drink = waterNorma.copy(drink = waterNorma.drink + drinking)
        initWaterNorma(drink)

    }

    private fun updateWaterNorma(){
        waterNormasLD.value = waterNormas.copy()
    }

}