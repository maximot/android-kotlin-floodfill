package com.maximot.floodfill.assemble

import android.content.Context
import com.maximot.floodfill.floodfill.data.*

class DependencyProvider(val context: Context) {
    val imageRepository: ImageRepository
            by lazy {
                ImageRepositoryImpl(context = context)
            }

    val imageProcessingService: ImageProcessingService
            by lazy {
                ImageProcessingServiceImpl()
            }

    val floodFillerRepository: FloodfillerRepository
            by lazy {
                FloodfillerRepositoryImpl(context = context)
            }

    val userSettingsRepository: UserSettingsRepository
            by lazy {
                UserSettingsRepositoryImpl(context = context)
            }

    val floodfillViewModelFactory: FloodfillViewModelFactory
            by lazy {
                FloodfillViewModelFactory(
                    imageRepository,
                    imageProcessingService,
                    floodFillerRepository,
                    userSettingsRepository
                )
            }
}