package com.qxy.siegelions.database

import androidx.room.Database
import androidx.room.TypeConverters
import com.qxy.siegelions.converter.DateConverter
import androidx.room.RoomDatabase
import com.qxy.siegelions.dao.RankingVersionDao
import com.qxy.siegelions.entity.RankingVersion

/**
 * Room数据库
 * @author yangqiangli
 */
@Database(entities = [RankingVersion::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class RankingVersionDatabase : RoomDatabase() {
    abstract val versionDao: RankingVersionDao?
}