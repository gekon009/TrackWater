package com.example.trackwater.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackwater.domain.*
import com.example.trackwater.data.*

class MainViewModel : ViewModel() {

    private val repositoryWaterNorma = WaterNormaRepositoryImpl
    private val repositorySetting = SettingRepositoryImpl

    private val getWaterNormaUseCase = GetWaterNormaUseCase(repositoryWaterNorma)
    private val editWaterNormaUseCase = EditWaterNormaUseCase(repositoryWaterNorma)
    private val initWaterNormaUseCase = InitWaterNormaUseCase(repositoryWaterNorma)
    private val addWaterNormaUseCase = AddWaterNormaUseCase(repositoryWaterNorma)
    private val addSettingUseCase = AddSettingUseCase(repositorySetting)
    private val editSettingUseCase = EditSettingUseCase(repositorySetting)
    private val getSettingUseCase = GetSettingUseCase(repositorySetting)

    val waterNormals = MutableLiveData<WaterNorma>()

    fun getWaterNorma(waterNormaId: Int = -1){
        waterNormals.value = getWaterNormaUseCase.getWaterNorma(waterNormaId)
    }

    fun changeDrinkWaterNorma(waterNorma: WaterNorma, drinking: Int) {
        addWaterNormaUseCase.addWaterNorma(waterNorma, drinking)
        getWaterNorma()
    }


}