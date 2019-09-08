package com.maximot.floodfill.floodfill.data

import com.maximot.floodfill.utils.FloodfillAlgorithm

interface UserSettingsRepository {
    fun saveUserSettings(us: UserSettings)
    fun loadUserSettings(): UserSettings
}

data class UserSettings(
    val fps: Int = 30,
    val algorithm: FloodfillAlgorithm = FloodfillAlgorithm.QUEUE
)