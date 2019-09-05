package com.maximot.floodfill.assemble

import android.content.Context
import com.maximot.floodfill.floodfill.data.ImageProcessingServiceImpl
import com.maximot.floodfill.floodfill.data.ImageRepository
import com.maximot.floodfill.floodfill.data.ImageRepositoryImpl

class DependencyProvider(val context: Context) {
    val imageRepository: ImageRepository
            by lazy {
                ImageRepositoryImpl(context = context)
            }

    val imageProcessingService: ImageProcessingServiceImpl
            by lazy {
                ImageProcessingServiceImpl()
            }

    val floodfillViewModelFactory: FloodfillViewModelFactory
            by lazy {
                FloodfillViewModelFactory(
                    imageRepository,
                    imageProcessingService
                )
            }
}