package com.maximot.floodfill.utils

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import kotlin.random.Random

object BitmapGenerator{
    fun generate(width: Int, height: Int, seed: Long = (System.currentTimeMillis() + (width and height))): Bitmap {
        val random = Random(seed)
        val bitmap = createBitmap(width, height, Bitmap.Config.RGB_565, false)
        for(i in 0 until width){
            for(j in 0 until height){
                val nextBool = random.nextBoolean()
                bitmap[i,j] = if(nextBool) Color.WHITE  else Color.BLACK
            }
        }
        return bitmap
    }

}