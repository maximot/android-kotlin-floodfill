package com.maximot.floodfill.assemble

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maximot.floodfill.floodfill.data.FloodfillerRepository
import com.maximot.floodfill.floodfill.data.ImageProcessingService
import com.maximot.floodfill.floodfill.data.ImageRepository
import com.maximot.floodfill.floodfill.viewmodel.FloodfillViewModel


@Suppress("UNCHECKED_CAST")
class FloodfillViewModelFactory(
    private val imageRepository: ImageRepository,
    private val imageProcessingService: ImageProcessingService,
    private val floodFillerRepository: FloodfillerRepository
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FloodfillViewModel::class.java)) {
            return FloodfillViewModel(
                imageRepository,
                imageProcessingService,
                floodFillerRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}