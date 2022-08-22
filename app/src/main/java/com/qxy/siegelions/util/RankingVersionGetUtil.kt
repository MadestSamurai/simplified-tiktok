package com.qxy.siegelions.util

import android.content.Context
import com.qxy.siegelions.web.RankingNetGet
import com.qxy.siegelions.database.RankingVersionDatabase
import androidx.room.Room
import android.widget.Toast
import java.util.*

/**
 * 排名版本综合获取工具类
 * @author MadSamurai
 */
class RankingVersionGetUtil(private val mContext: Context) {
    private var rankingNetGet: RankingNetGet? = null
    fun saveRankingVersion(type: Int) {
        rankingNetGet = RankingNetGet(mContext)
        val rankingVersionDatabase = arrayOf(
            Room.databaseBuilder(
                mContext, RankingVersionDatabase::class.java, "ranking_Version_database"
            )
                .allowMainThreadQueries()
                .build()
        )
        val rankingVersionDao = rankingVersionDatabase[0].versionDao
        if (NetCheckUtil.isNetConnection(mContext)) {
            val thread = Thread {
                if (NetCheckUtil.isOnline()) {
                    var hasMore = true
                    var hasGet = false
                    var cursor = 0
                    while (hasMore && !hasGet) {
                        val rankingVersionReq =
                            rankingNetGet!!.getRankingVersion(cursor.toLong(), 20, type)
                        cursor = rankingVersionReq.cursor
                        hasMore = rankingVersionReq.hasMore == true
                        for (rankingVersion in rankingVersionReq.rankingVersion) {
                            assert(rankingVersionDao != null)
                            if (Objects.requireNonNull(rankingVersion.activeTime)?.let { rankingVersionDao!!.getVersionByDate(it) } != null) {
                                hasGet = true
                            } else rankingVersionDao?.insertVersion(rankingVersion)
                        }
                    }
                } else {
                    Toast.makeText(mContext, "无网络链接", Toast.LENGTH_LONG).show()
                }
            }
            thread.start()
        } else {
            Toast.makeText(mContext, "无网络链接", Toast.LENGTH_LONG).show()
        }
    }
}