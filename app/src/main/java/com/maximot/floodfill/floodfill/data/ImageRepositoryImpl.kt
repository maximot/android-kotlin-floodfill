package com.maximot.floodfill.floodfill.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ImageRepositoryImpl(val context: Context) : ImageRepository{
    companion object{
        private const val IMAGE_NAME = "image.png"
    }

    val imageFile = File(context.filesDir, IMAGE_NAME)

    override fun save(image: Bitmap): Boolean {
        FileOutputStream(imageFile).use { out ->
            image.compress(Bitmap.CompressFormat.PNG,100,out)
            return true
        }
        return false
    }

    override fun load(): Bitmap {
        val decodeStream = BitmapFactory.decodeStream(FileInputStream(imageFile))
        val bitmap = decodeStream.copy(Bitmap.Config.RGB_565,true)
        decodeStream.recycle()
        return bitmap
    }
}