package com.example.trackwater.domain

interface SettingRepository {

    fun getSetting(settingID: Int): Setting

    fun addSetting(setting: Setting)

    fun editSetting(setting: Setting)


}