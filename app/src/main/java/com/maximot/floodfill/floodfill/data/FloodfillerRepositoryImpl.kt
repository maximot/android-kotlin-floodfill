package com.maximot.floodfill.floodfill.data

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Parcel
import android.util.Base64
import android.util.Base64.DEFAULT
import androidx.core.content.edit
import com.maximot.floodfill.utils.Floodfiller

class FloodfillerRepositoryImpl(private val context: Context): FloodfillerRepository {

    companion object{
        private const val PREFS_NAME = "FLOODFILLERS"
        private const val PREFS_FILLERS_ARG = "FLOODFILLERS"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)

    override fun saveFloodfillers(list: List<Floodfiller>){
        val parcelStringSet = list.map {
            val parcel = Parcel.obtain()
            parcel.setDataPosition(0)
            it.writeToParcel(parcel)
            val string = Base64.encodeToString(parcel.marshall(), DEFAULT)
            parcel.recycle()
            string
        }.toSet()

        sharedPreferences.edit {
            this.putStringSet(PREFS_FILLERS_ARG, parcelStringSet)
        }
    }

    override fun getFloodfillersFor(image: Bitmap): List<Floodfiller> {
        return sharedPreferences.getStringSet(PREFS_FILLERS_ARG, emptySet())!!
            .map { Base64.decode(it, DEFAULT) }
            .map {
                val parcel = Parcel.obtain()
                parcel.setDataPosition(0)
                parcel.unmarshall(it, 0, it.size)
                parcel.setDataPosition(0)
                val floodfiller = Floodfiller.create(image, parcel)
                parcel.recycle()
                floodfiller
            }.toMutableList()
    }
}