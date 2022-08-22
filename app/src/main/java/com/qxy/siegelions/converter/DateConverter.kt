package com.qxy.siegelions.converter

import androidx.room.TypeConverter
import java.util.*

/**
 * Room日期转换器
 * @author yangqiangli
 */
object DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}