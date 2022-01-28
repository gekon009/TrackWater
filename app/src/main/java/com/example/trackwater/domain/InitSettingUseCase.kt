package com.example.trackwater.domain

class InitSettingUseCase(private val settingRepository: SettingRepository) {

    fun initSetting(setting: Setting) {
        settingRepository.initSetting(setting)
    }

}