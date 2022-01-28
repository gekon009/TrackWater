package com.example.trackwater.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackwater.domain.*
import com.example.trackwater.data.*

class MainViewModel : ViewModel() {

    private val repositoryWaterNorma = WaterNormaRepositoryImpl
    private val repositorySetting = SettingRepositoryImpl

    private val getWaterNormaUseCase = GetWaterNormaUseCase(repositoryWaterNorma)
    private val initWaterNormaUseCase = InitWaterNormaUseCase(repositoryWaterNorma)
    private val drinkWaterNormaUseCase = DrinkWaterNormaUseCase(repositoryWaterNorma)
    private val initSettingUseCase = InitSettingUseCase(repositorySetting)
    private val getSettingUseCase = GetSettingUseCase(repositorySetting)

    val waterNormals = getWaterNormaUseCase.getWaterNorma()
    val settings = getSettingUseCase.getSetting()

    fun drinkWaterNorma(waterNorma: WaterNorma, drinking: Int) {
        drinkWaterNormaUseCase.drinkWaterNorma(waterNorma, drinking)
    }



}