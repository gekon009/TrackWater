package com.example.trackwater.presentation

import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackwater.R
import com.example.trackwater.data.SettingRepositoryImpl
import com.example.trackwater.data.WaterNormaRepositoryImpl
import com.example.trackwater.domain.*
import java.lang.RuntimeException

class SettingViewModel: ViewModel() {

    private val repositoryWaterNorma = WaterNormaRepositoryImpl
    private val repositorySetting = SettingRepositoryImpl

    private val initWaterNormaUseCase = InitWaterNormaUseCase(repositoryWaterNorma)
    private val getWaterNormaUseCase = GetWaterNormaUseCase(repositoryWaterNorma)

    private val getSettingUseCase = GetSettingUseCase(repositorySetting)
    private val initSettingUseCase = InitSettingUseCase(repositorySetting)

    private val _errorInputGender = MutableLiveData<Boolean>()
    val errorInputGender: LiveData<Boolean>
        get() = _errorInputGender

    private val _errorInputAge = MutableLiveData<Boolean>()
    val errorInputAge: LiveData<Boolean>
        get() = _errorInputAge

    private val _errorInputActivity = MutableLiveData<Boolean>()
    val errorInputActivity: LiveData<Boolean>
        get() = _errorInputActivity

    private val _errorInputWeight = MutableLiveData<Boolean>()
    val errorInputWeight: LiveData<Boolean>
        get() = _errorInputWeight

    private val _setting = MutableLiveData<Setting>()
    val setting: LiveData<Setting>
        get() = _setting



    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen


    fun getSetting() {
        val set = getSettingUseCase.getSetting()
        _setting.value = set
    }

    fun initSetting(rgGender: RadioGroup, age: String?
                            , rgActivity: RadioGroup, weight: String?) {
        val gender = parseRG(rgGender)
        val age = parseAge(age)
        val activity = parseRG(rgActivity)
        val weight = parseWeight(weight)

        val s = Setting(gender = gender, age = age, activity = activity,weight = weight)
        initSettingUseCase.initSetting(s)
        _setting.value = s

        val water = getWaterNormaUseCase.getWaterNorma().copy()
        water.norma = norm4weight(weight = weight, activity = activity)
        initWaterNorma(water)


    }

    private fun initWaterNorma(waterNorma: WaterNorma){
        initWaterNormaUseCase.initWaterNorma(waterNorma)
        finishWork()
    }

    private fun parseRG(rg: RadioGroup) : Int {
        return if (validateRG(rg)) {
            var rb = rg.findViewById<RadioButton>(rg.checkedRadioButtonId)
            rb.tag.toString().toInt()
        } else -1
    }

    private fun validateRG(rg: RadioGroup) : Boolean{
        return if (rg.checkedRadioButtonId == -1){
            when(rg.tag.toString().toInt()){
                1 -> _errorInputGender.value = true
                3 -> _errorInputActivity.value = true
            }
            false
        } else true

    }

    private fun parseAge(str: String?) :Int {
        var s = str?.trim() ?: ""
        return try {
            if(validateAge(s)) s.toInt()
            else -1
        } catch (e: Exception) {
            -1
        }
    }

    private fun validateAge(str: String) : Boolean{
        var result = true
        if(str.isBlank()){
            result = false
            _errorInputAge.value = true
        }
        if(str.toInt() < 0){
            result = false
            _errorInputAge.value = true
        }
        return result
    }

    private fun parseWeight(str: String?) :Int {
        var s = str?.trim() ?: ""
        return try {
            if(validateWeight(s)) s.toInt()
            else -1
        } catch (e: Exception) {
            logging(e.toString())
            logging(s)
            -1
        }
    }

    private fun validateWeight(str: String) : Boolean{
        var result = true
        if(str.isBlank()){
            result = false
            _errorInputWeight.value = true
        }
        if(str.toInt() < 0){
            result = false
            _errorInputWeight.value = true
        }
        return result
    }

    private fun resetErrorInputGender() {
        _errorInputGender.value = false
    }

    private fun resetErrorInputAge() {
        _errorInputAge.value = false
    }

    private fun resetErrorInputActivity() {
        _errorInputActivity.value = false
    }

    private fun resetErrorInputWeight() {
        _errorInputWeight.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }

    private fun norm4weight(weight: Int, activity: Int): Int {
        var w = weight / 10
        var water = arrayOf(
            normWater(0, 1250),
            normWater(1, 1700),
            normWater(2, 2000)
        )
        when(w){
            5 -> {
                water = arrayOf(
                    normWater(0, 1550),
                    normWater(1, 2000),
                    normWater(2, 2300)
                )
            }
            6 -> {
                water = arrayOf(
                    normWater(0, 1850),
                    normWater(1, 2300),
                    normWater(2, 2650)
                )
            }
            7 -> {
                water = arrayOf(
                    normWater(0, 2200),
                    normWater(1, 2550),
                    normWater(2, 3000)
                )
            }
            8 -> {
                water = arrayOf(
                    normWater(0, 2500),
                    normWater(1, 2950),
                    normWater(2, 3300)
                )
            }
            9 -> {
                water = arrayOf(
                    normWater(0, 2800),
                    normWater(1, 3300),
                    normWater(2, 3600)
                )
            }
            10 -> {
                water = arrayOf(
                    normWater(0, 3100),
                    normWater(1, 3600),
                    normWater(2, 3900)
                )
            }
            11 -> {
                water = arrayOf(
                    normWater(0, 3400),
                    normWater(1, 3900),
                    normWater(2, 4100)
                )
            }
            in 12..100 -> {
                water = arrayOf(
                    normWater(0, 3700),
                    normWater(1, 4200),
                    normWater(2, 4400)
                )
            }
        }
        return water[activity].norm
    }

    class normWater(activity: Int, norm: Int){
        var activity = activity
        var norm = norm
    }


    fun logging(str: String){
        Log.d("MainViewTest", str)
    }

    fun getGender(it: Setting): Int{
        return when(it.gender) {
            0 -> R.id.rbMen
            1 -> R.id.rbWomen
            else -> throw RuntimeException("Пол не указан")
        }
    }

    fun getActivity(it: Setting): Int{
        return when(it.activity) {
            0 -> R.id.rbLow
            1 -> R.id.rbMiddle
            2 -> R.id.rbHigh
            else -> throw RuntimeException("Активность не указана")
        }
    }

}