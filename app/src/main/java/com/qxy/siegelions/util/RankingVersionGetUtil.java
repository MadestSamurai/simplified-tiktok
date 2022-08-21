package com.qxy.siegelions.util;

import android.content.Context;
import android.widget.Toast;

import androidx.room.Room;

import com.qxy.siegelions.dao.RankingVersionDao;
import com.qxy.siegelions.database.RankingVersionDatabase;
import com.qxy.siegelions.entity.RankingVersion;
import com.qxy.siegelions.entity.RankingVersionReq;
import com.qxy.siegelions.web.RankingNetGet;

import java.util.Objects;

public class RankingVersionGetUtil {
    private final Context mContext;

    private RankingNetGet rankingNetGet;

    public RankingVersionGetUtil(Context mContext) {
        this.mContext = mContext;
    }

    public void saveRankingVersion(int type) {

        rankingNetGet = new RankingNetGet(mContext);
        RankingVersionDatabase[] rankingVersionDatabase = {Room.databaseBuilder(mContext, RankingVersionDatabase.class, "ranking_Version_database")
                .allowMainThreadQueries()
                .build()};
        RankingVersionDao rankingVersionDao = rankingVersionDatabase[0].getVersionDao();

        if (NetCheckUtil.isNetConnection(mContext)) {
            Thread thread = new Thread(() -> {
                if (NetCheckUtil.isOnline()) {
                    Boolean hasMore = true;
                    int cursor = 0;
                    while (Boolean.TRUE.equals(hasMore)) {
                        RankingVersionReq rankingVersionReq = rankingNetGet.getRankingVersion(cursor, 20, type);
                        cursor = rankingVersionReq.getCursor();
                        hasMore = rankingVersionReq.getHasMore();
                        for (RankingVersion rankingVersion : rankingVersionReq.getRankingVersion()) {
                            assert rankingVersionDao != null;
                            if (rankingVersionDao.getVersionByDate(Objects.requireNonNull(rankingVersion.getActiveTime())) != null) {
                                rankingVersionDao.updateVersion(rankingVersion);
                            } else rankingVersionDao.insertVersion(rankingVersion);
                        }
                    }
                } else {
                    Toast.makeText(mContext, "无网络链接", Toast.LENGTH_LONG).show();
                }
            });
            thread.start();
        } else {
            Toast.makeText(mContext, "无网络链接", Toast.LENGTH_LONG).show();
        }
    }
}
