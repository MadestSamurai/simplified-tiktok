package com.qxy.siegelions.converter

import androidx.room.TypeConverter
import com.google.gson.Gson

object StringConverter {
    @TypeConverter
    fun revert(listStr: String?): Array<String>? {
        return Gson().fromJson(listStr, Array<String>::class.java)
    }

    @TypeConverter
    fun converter(list: Array<String?>?): String {
        return Gson().toJson(list)
    }
}