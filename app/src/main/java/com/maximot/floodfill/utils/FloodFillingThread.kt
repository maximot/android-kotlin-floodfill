package com.maximot.floodfill.utils

import android.graphics.Bitmap
import android.graphics.Point
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.max
import kotlin.math.roundToInt

class FloodFillingThread(
    val image: Bitmap,
    val point: Point,
    val color: Int,
    val floodfillAlgorithm: FloodfillAlgorithm
) : Thread() {
    private var flagRunning: AtomicBoolean = AtomicBoolean(false)
    var isRunning
        get() = flagRunning.get()
        set(value) = flagRunning.set(value)

    private var safeFps = AtomicInteger(60)

    var fps: Int
        get() = safeFps.get()
        set(value) = safeFps.set(value)

    private val frameTime: Int
        get() {
            return (1000 / fps.toFloat()).roundToInt()
        }

    fun startFilling() {
        isRunning = true
        super.start()
    }

    fun stopFilling() {
        var retry = true
        isRunning = false
        while (retry) {
            try {
                join()
                retry = false
            } catch (e: InterruptedException) {

            }
        }
    }

    override fun run() {
        val floodfiller = when (floodfillAlgorithm) {
            FloodfillAlgorithm.QUEUE -> Floodfiller.Queue(image, point, color)
            FloodfillAlgorithm.STACK -> Floodfiller.Stack(image, point, color)
            FloodfillAlgorithm.SCANLINE -> Floodfiller.Scanline(image, point, color)
        }

        while (isRunning) {
            var beforeTime = System.currentTimeMillis()
            synchronized(image) {
                floodfiller.step()
            }
            if(floodfiller.isDone)
                isRunning = false
            var afterTime = System.currentTimeMillis()
            val elapsedTime = afterTime - beforeTime
            sleep(max(0, frameTime - elapsedTime))
        }
    }
}