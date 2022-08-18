package com.qxy.siegelions.database

import androidx.room.Database
import com.qxy.siegelions.entity.RankingEntry
import androidx.room.TypeConverters
import com.qxy.siegelions.converter.DateConverter
import androidx.room.RoomDatabase
import com.qxy.siegelions.converter.StringConverter
import com.qxy.siegelions.dao.RankingEntryDao

@Database(entities = [RankingEntry::class], version = 1)
@TypeConverters(StringConverter::class, DateConverter::class)
abstract class RankingEntryDatabase : RoomDatabase() {
    abstract val entryDao: RankingEntryDao?
}