package com.maximot.floodfill.floodfill.data

import android.graphics.Bitmap
import com.maximot.floodfill.utils.Floodfiller

interface FloodfillerRepository {
    fun saveFloodfillers(list: List<Floodfiller>)
    fun getFloodfillersFor(image: Bitmap): List<Floodfiller>
}