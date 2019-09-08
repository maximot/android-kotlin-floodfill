package com.maximot.floodfill.floodfill.data

import com.maximot.floodfill.utils.Floodfiller
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.max
import kotlin.math.roundToInt

class FloodFillingExecutor() {

    private var floodfillerList: ArrayList<Floodfiller> = arrayListOf()

    private var safeFps = AtomicInteger(60)

    var fps: Int
        get() = safeFps.get()
        set(value) = safeFps.set(value)

    private val frameTime: Int
        get() {
            return (1000 / fps.toFloat()).roundToInt()
        }

    private var flagRunning: AtomicBoolean = AtomicBoolean(false)
    var isRunning
        get() = flagRunning.get()
        set(value) = flagRunning.set(value)


    private var fillingThread: FloodfillingThread? = null

    fun start() {
        if(isRunning)
            return
        if(fillingThread != null)
            throw IllegalStateException("Something went wrong!")
        isRunning = true
        fillingThread = FloodfillingThread()
        fillingThread?.start()
    }

    fun stop() {
        if(!isRunning)
            return
        var retry = true
        isRunning = false
        while (retry) {
            try {
                fillingThread?.join()
                retry = false
                fillingThread = null
            } catch (e: InterruptedException) {

            }
        }
    }

    fun isEmpty() = synchronized(floodfillerList){
        floodfillerList.isEmpty()
    }

    fun addFiller(floodfiller: Floodfiller) = synchronized(floodfillerList){
        floodfillerList.add(floodfiller)
    }

    fun removeFiller(floodfiller: Floodfiller) = synchronized(floodfillerList){
        floodfillerList.remove(floodfiller)
    }

    fun clear() = synchronized(floodfillerList){
        floodfillerList.clear()
    }

    private inner class FloodfillingThread: Thread(){
        override fun run() {
            while(isRunning) {
                var beforeTime = System.currentTimeMillis()
                synchronized(floodfillerList) {
                    val activeFillers = floodfillerList.filter { !it.isDone }
                    activeFillers.forEach {
                        synchronized(it.image){
                            it.step()
                        }
                    }
                    floodfillerList.removeAll { it.isDone }
                }
                var afterTime = System.currentTimeMillis()
                val elapsedTime = afterTime - beforeTime
                sleep(max(0, frameTime - elapsedTime))
            }
        }
    }
}