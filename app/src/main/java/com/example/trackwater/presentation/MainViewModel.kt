package com.example.trackwater.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackwater.domain.*
import com.example.trackwater.data.*

class MainViewModel : ViewModel() {

    private val repositoryWaterNorma = WaterNormaRepositoryImpl

    private val getWaterNormaUseCase = GetWaterNormaUseCase(repositoryWaterNorma)
    private val initWaterNormaUseCase = InitWaterNormaUseCase(repositoryWaterNorma)

    private val _waterNorma = MutableLiveData<WaterNorma>()
    val waterNorma: LiveData<WaterNorma>
        get() = _waterNorma

    fun drinkWaterNorma(drinking: Int) {
        _waterNorma.value?.let{
            val norm = it.copy()
            norm.drink = if(it.drink + drinking < -1) 0 else it.drink + drinking
            initWaterNorma(norm)
            _waterNorma.value = norm
        }
    }

    fun getWaterNorma() {
        val norm = getWaterNormaUseCase.getWaterNorma()
        _waterNorma.value = norm
    }

    private fun initWaterNorma(waterNorma: WaterNorma){
        initWaterNormaUseCase.initWaterNorma(waterNorma)
    }
}
