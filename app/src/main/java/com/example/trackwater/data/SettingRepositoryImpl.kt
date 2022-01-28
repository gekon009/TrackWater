package com.example.trackwater.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trackwater.domain.Setting
import com.example.trackwater.domain.SettingRepository

object SettingRepositoryImpl: SettingRepository {

    private lateinit var settings : Setting
    private var settingsLD = MutableLiveData<Setting>()

    init {
        val set = Setting(2, 1, 28, 82)
        initSetting(set)
    }

    override fun initSetting(setting: Setting) {
        settings = Setting(setting.activity, setting.gender, setting.age, setting.weight)
        updateSetting()
    }

    override fun getSetting(): LiveData<Setting> {
        return settingsLD
    }

    private fun updateSetting() {
        settingsLD.value = settings.copy()
    }
}