package com.qxy.siegelions.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.qxy.siegelions.entity.RankingEntry
import androidx.room.Update

/**
 * Room Dao
 * @author yangqiangli
 */
@Dao
interface RankingEntryDao {
    @Insert
    fun insertEntry(rankingEntry: RankingEntry?)

    @Update
    fun updateEntry(rankingEntry: RankingEntry?)

    @Query("SELECT * FROM ranking_entry WHERE version_id = :vid ORDER BY rank")
    fun allEntryByVersion(vid: Int): Array<RankingEntry>?

    @Query("SELECT id FROM ranking_entry WHERE version_id = :version AND rank = :rank")
    fun getIdByVersionAndRank(version: Int, rank: Int): Long?
}