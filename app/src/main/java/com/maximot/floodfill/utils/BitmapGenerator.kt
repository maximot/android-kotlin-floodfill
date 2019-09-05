package com.maximot.floodfill.utils

import android.graphics.*
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import kotlin.math.max
import kotlin.math.sqrt
import kotlin.random.Random


object BitmapGenerator {
    fun generate(
        width: Int,
        height: Int,
        seed: Long = (System.currentTimeMillis() + (width and height))
    ): Bitmap {
        val random = Random(seed)
        val bitmap = createBitmap(width, height, Bitmap.Config.RGB_565, false)
        for (i in 0 until width) {
            for (j in 0 until height) {
                val nextBool = random.nextBoolean()
                bitmap[i, j] = if (nextBool) Color.WHITE else Color.BLACK
            }
        }
        addRocketLogo(width, height, random, bitmap)


        return bitmap
    }

    private fun addRocketLogo(width: Int, height: Int, random: Random, bitmap: Bitmap) {
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = Color.BLACK
        paint.strokeWidth = 0.9f
        paint.strokeJoin = Paint.Join.MITER
        paint.strokeMiter = 0.1f
        paint.strokeCap = Paint.Cap.ROUND
        paint.isAntiAlias = false

        val size = random.nextInt(5, max(6, width / 8)).toFloat()

        val rocketY = random.nextInt((size / 2).toInt(), max((size / 2).toInt(), height)).toFloat()
        val rocketX = random.nextInt((size / 2).toInt(), max((size / 2).toInt(), width)).toFloat()


        canvas.drawCircle(rocketX + size / 4, rocketY - size / 4, size * 2f, paint)
        paint.color = Color.WHITE
        val path = Path()
        path.moveTo(rocketX + size, rocketY - size)
        path.lineTo(rocketX, rocketY + size)
        path.moveTo(rocketX, rocketY + size)
        path.lineTo(rocketX - size, rocketY)
        path.moveTo(rocketX - size, rocketY)
        path.lineTo(rocketX + size, rocketY - size)
        path.close()
        canvas.drawPath(path, paint)
        canvas.drawLine(rocketX + size - 1, rocketY - size + 1, rocketX, rocketY + size, paint)

        Floodfiller.Scanline(bitmap, Point(rocketX.toInt(), rocketY.toInt()), Color.WHITE).fill()

        paint.color = Color.BLACK

        val radius = sqrt(
            2 * size * size
        ).toFloat() / 2.0f
        canvas.drawCircle((2 * rocketX - size) / 2, (2 * rocketY + size) / 2, radius, paint)

    }

}