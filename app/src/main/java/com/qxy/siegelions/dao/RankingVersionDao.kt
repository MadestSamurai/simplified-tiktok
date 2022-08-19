package com.qxy.siegelions.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.qxy.siegelions.entity.RankingVersion
import java.util.*

@Dao
interface RankingVersionDao {
    @Insert
    fun insertVersion(rankingEntry: RankingVersion?)

    @Update
    fun updateVersion(rankingEntry: RankingVersion?)

    @Query("SELECT version FROM ranking_version WHERE active_time < :time ORDER BY active_time DESC limit 1")
    fun getVersionIdByDate(time: Date): Long

    @Query("SELECT * FROM ranking_version WHERE active_time = :time")
    fun getVersionByDate(time: Date): RankingVersion

    @Query("SELECT * FROM ranking_version WHERE id = :vid")
    fun getVersionById(vid: Long): RankingVersion?
}