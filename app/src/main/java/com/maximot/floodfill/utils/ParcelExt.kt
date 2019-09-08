package com.maximot.floodfill.utils

import android.os.Parcel

inline fun <reified T : Enum<T>> Parcel.readEnum() =
    readString()?.let { enumValueOf<T>(it) }

inline fun <T : Enum<T>> Parcel.writeEnum(value: T?) =
    writeString(value?.name)