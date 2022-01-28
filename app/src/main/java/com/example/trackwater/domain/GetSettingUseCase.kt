package com.example.trackwater.domain

import androidx.lifecycle.LiveData

class GetSettingUseCase (private val settingRepository: SettingRepository) {

    fun getSetting(): LiveData<Setting>{
        return settingRepository.getSetting()
    }
}