package com.maximot.floodfill.floodfill.data

import android.graphics.Bitmap
import android.graphics.Color
import com.maximot.floodfill.utils.FloodFillingThread
import com.maximot.floodfill.utils.FloodfillAlgorithm

interface ImageProcessingService{
    fun generateImage(width: Int, height: Int): Bitmap
    fun floodfillImagePart(image: Bitmap,
                           x: Int, y: Int,
                           color: Int = Color.RED,
                           algorithm: FloodfillAlgorithm = FloodfillAlgorithm.QUEUE)
            : FloodFillingThread
}