package com.maximot.floodfill.floodfill.data

import android.graphics.Bitmap
import android.graphics.Point
import com.maximot.floodfill.utils.BitmapGenerator
import com.maximot.floodfill.utils.FloodFillingThread
import com.maximot.floodfill.utils.FloodfillAlgorithm
import java.lang.Math.random
import kotlin.math.cos

class ImageProcessingServiceImpl : ImageProcessingService {
    override fun generateImage(width: Int, height: Int): Bitmap {
        val seed = (
                    (System.nanoTime() + (cos((width and height).toFloat()) * 1000)) * random()
                ).toLong()
        return BitmapGenerator.generate(width, height, seed)
    }

    override fun floodfillImagePart(
        image: Bitmap,
        x: Int, y: Int,
        color: Int,
        algorithm: FloodfillAlgorithm
    ): FloodFillingThread {
        return FloodFillingThread(image, Point(x, y), color, algorithm)
    }
}