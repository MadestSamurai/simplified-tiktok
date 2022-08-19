package com.qxy.siegelions.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.qxy.siegelions.entity.RankingEntry
import androidx.room.Update

@Dao
interface RankingEntryDao {
    @Insert
    fun insertEntry(rankingEntry: RankingEntry?)

    @Update
    fun updateEntry(rankingEntry: RankingEntry?)

    @Query("SELECT * FROM ranking_entry WHERE version_id = :vid ORDER BY rank")
    fun allEntryByVersion(vid: Long): Array<RankingEntry>?

    @Query("SELECT * FROM ranking_entry WHERE id = :eid")
    fun getEntryById(eid: String): RankingEntry?
}