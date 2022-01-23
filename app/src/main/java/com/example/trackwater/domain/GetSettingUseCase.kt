package com.example.trackwater.domain

class GetSettingUseCase (private val settingRepository: SettingRepository) {

    fun getSetting(settingID: Int): Setting{
        return settingRepository.getSetting(settingID)
    }
}