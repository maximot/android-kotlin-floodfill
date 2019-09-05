package com.maximot.floodfill.floodfill.data

import android.graphics.Bitmap

interface ImageRepository {
    fun save(image: Bitmap): Boolean
    fun load(): Bitmap
}