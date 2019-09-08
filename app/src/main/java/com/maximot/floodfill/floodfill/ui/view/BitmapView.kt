package com.maximot.floodfill.floodfill.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_UP
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.concurrent.atomic.AtomicBoolean


class BitmapView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {
    companion object {
        private const val DELAY_MS = 16
    }

    var image: Bitmap? = null
        set(value) {
            onPreChangeBitmap(image)
            field = value
            onChangeBitmap(image)
        }

    var onBitmapClickListener: OnBitmapClickListener? = null

    private var isSurfaceCreated = false

    private var bitmapDrawThread: BitmapDrawThread? = null

    init {
        holder.addCallback(this)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == ACTION_UP) {
            val viewX = event.x
            val viewY = event.y

            if (viewX < 0 || viewY < 0 || viewX > width || viewY > height)
                return true


            val bitmapX = ((viewX / width) * (image?.width ?: 0)).toInt()
            val bitmapY = ((viewY / height) * (image?.height ?: 0)).toInt()

            onBitmapClickListener?.onBitmapClicked(bitmapX, bitmapY)

        }
        return true
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        isSurfaceCreated = true
        onSurfaceCreated()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        isSurfaceCreated = false
        onSurfaceDestroyed()
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) = Unit

    private fun onSurfaceCreated() {
        if (image != null) {
            startBitmapRendering()
        }
    }

    private fun onSurfaceDestroyed() {
        stopBitmapRendering()
    }

    private fun onPreChangeBitmap(image: Bitmap?) {
        if (bitmapDrawThread != null || image == null)
            stopBitmapRendering()
    }

    private fun onChangeBitmap(image: Bitmap?) {
        if (image != null && isSurfaceCreated) {
            startBitmapRendering()
        }
    }

    private fun startBitmapRendering() {
        if (bitmapDrawThread != null)
            return
        bitmapDrawThread = BitmapDrawThread(holder, image!!)
        bitmapDrawThread!!.startDrawing()
    }

    private fun stopBitmapRendering() {
        bitmapDrawThread?.stopDrawing()
        bitmapDrawThread = null
    }

    private class BitmapDrawThread(
        private val surfaceHolder: SurfaceHolder,
        private val image: Bitmap
    ) :
        Thread() {
        private var time = System.currentTimeMillis()

        private var flagRunning: AtomicBoolean = AtomicBoolean(false)
        var isRunning
            get() = flagRunning.get()
            set(value) = flagRunning.set(value)

        fun startDrawing() {
            isRunning = true
            super.start()
        }

        fun stopDrawing() {
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
            var canvas: Canvas?
            while (isRunning) {
                val now = System.currentTimeMillis()
                val elapsedTime = now - time
                canvas = null
                if (elapsedTime > DELAY_MS) {
                    time = now
                    try {
                        canvas = surfaceHolder.lockCanvas(null)
                        if (canvas == null)
                            return
                        synchronized(surfaceHolder) {
                            canvas.drawColor(Color.BLACK)
                            val dest = Rect(0, 0, canvas.width - 1, canvas.height - 1)
                            synchronized(image) {
                                val src = Rect(0, 0, image.width - 1, image.height - 1)
                                canvas.drawBitmap(image, src, dest, null)
                            }
                        }
                    } finally {
                        if (canvas != null) {
                            surfaceHolder.unlockCanvasAndPost(canvas)
                        }
                    }
                }
            }

        }
    }

    interface OnBitmapClickListener {
        fun onBitmapClicked(x: Int, y: Int)
    }
}