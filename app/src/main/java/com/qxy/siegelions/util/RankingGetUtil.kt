package com.qxy.siegelions.util

import android.content.Context
import android.util.Log
import com.qxy.siegelions.web.RankingNetGet
import com.qxy.siegelions.entity.RankingEntry
import com.qxy.siegelions.database.RankingEntryDatabase
import androidx.room.Room
import android.widget.Toast
import com.qxy.siegelions.database.RankingVersionDatabase
import java.util.*

/**
 * 排名获取综合工具类
 * @author MadSamurai
 */
class RankingGetUtil(private val mContext: Context) {
    private var rankingNetGet: RankingNetGet? = null
    fun getRankingEntry(type: Int, version: Int): Array<RankingEntry>? {
        val rankingEntries = arrayOf<Array<RankingEntry>?>(null)
        rankingNetGet = RankingNetGet(mContext)
        val rankingEntryDatabase = arrayOf(
            Room.databaseBuilder(
                mContext, RankingEntryDatabase::class.java, "ranking_entry_database"
            )
                .allowMainThreadQueries()
                .build()
        )
        val rankingEntryDao = rankingEntryDatabase[0].entryDao
        if (NetCheckUtil.isNetConnection(mContext)) {
            val thread = Thread {
                if (NetCheckUtil.isOnline()) {
                    val rankingEntryReq = rankingNetGet!!.getRanking(type, version)
                    Log.d("version_id", version.toString() + "")
                    assert(rankingEntryReq != null)
                    rankingEntries[0] = rankingEntryReq!!.rankingEntry
                    for (rankingEntry in rankingEntries[0]!!) {
                        assert(rankingEntryDao != null)
                        rankingEntry.versionId = version
                        val id = rankingEntryDao!!.getIdByVersionAndRank(version, rankingEntry.rank)
                        Log.d("entry_id", id.toString() + "")
                        if (id != null) {
                            rankingEntry.id = id
                            rankingEntryDao.updateEntry(rankingEntry)
                        } else rankingEntryDao.insertEntry(rankingEntry)
                    }
                } else {
                    Toast.makeText(mContext, "无网络链接", Toast.LENGTH_LONG).show()
                    assert(rankingEntryDao != null)
                    rankingEntries[0] = rankingEntryDao!!.allEntryByVersion(version)
                }
            }
            thread.start()
            try {
                thread.join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        } else {
            Toast.makeText(mContext, "无网络链接", Toast.LENGTH_LONG).show()
            assert(rankingEntryDao != null)
            rankingEntries[0] = rankingEntryDao!!.allEntryByVersion(version)
        }
        return rankingEntries[0]
    }

    fun getRankingEntry(type: Int): Array<RankingEntry>? {
        val rankingVersionDatabase = Room.databaseBuilder(
            mContext, RankingVersionDatabase::class.java, "ranking_version_database"
        )
            .allowMainThreadQueries()
            .build()
        val rankingVersionDao = rankingVersionDatabase.versionDao!!
        val version = rankingVersionDao.getVersionIdByDate(Date())
        return this.getRankingEntry(type, version)
    }
}