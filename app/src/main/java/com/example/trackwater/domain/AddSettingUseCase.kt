package com.example.trackwater.domain

class AddSettingUseCase(private val settingRepository: SettingRepository) {

    fun addSetting(setting: Setting) {
        settingRepository.addSetting(setting)
    }

}