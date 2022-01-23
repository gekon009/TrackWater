package com.example.trackwater.data

import com.example.trackwater.domain.Setting
import com.example.trackwater.domain.SettingRepository
import java.lang.RuntimeException

object SettingRepositoryImpl: SettingRepository {

    private val settings = mutableListOf<Setting>()

    init {
        val set = Setting(2, 1, 28, 82)
        addSetting(set)
    }

    override fun getSetting(settingID: Int): Setting {
        return settings.find {
            it.id == settingID
        } ?: throw RuntimeException("Setting $settingID not found")
    }

    override fun addSetting(setting: Setting) {
        settings.add(setting)
    }

    override fun editSetting(setting: Setting) {
        val oldSetting = getSetting(setting.id)
        settings.remove(oldSetting)
        addSetting(setting)
    }

}