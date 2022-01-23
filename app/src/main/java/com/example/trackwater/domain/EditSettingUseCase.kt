package com.example.trackwater.domain

class EditSettingUseCase (private val settingRepository: SettingRepository) {

    fun editSetting(setting: Setting) {
        settingRepository.editSetting(setting)
    }
}