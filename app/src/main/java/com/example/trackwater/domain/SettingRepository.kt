package com.example.trackwater.domain

import androidx.lifecycle.LiveData

interface SettingRepository {

    fun getSetting(): LiveData<Setting>

    fun initSetting(setting: Setting)

}