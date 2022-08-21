package com.qxy.siegelions.util;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.room.Room;

import com.qxy.siegelions.dao.RankingEntryDao;
import com.qxy.siegelions.dao.RankingVersionDao;
import com.qxy.siegelions.database.RankingEntryDatabase;
import com.qxy.siegelions.database.RankingVersionDatabase;
import com.qxy.siegelions.entity.RankingEntry;
import com.qxy.siegelions.entity.RankingEntryReq;
import com.qxy.siegelions.web.RankingNetGet;

import java.util.Date;
import java.util.Objects;

public class RankingGetUtil {
    private final Context mContext;

    private RankingNetGet rankingNetGet;

    public RankingGetUtil(Context mContext) {
        this.mContext = mContext;
    }

    public RankingEntry[] getRankingEntry(int type, int version) {
        final RankingEntry[][] rankingEntries = {null};

        rankingNetGet = new RankingNetGet(mContext);
        RankingEntryDatabase[] rankingEntryDatabase = {Room.databaseBuilder(mContext, RankingEntryDatabase.class, "ranking_entry_database")
                .allowMainThreadQueries()
                .build()};
        RankingEntryDao rankingEntryDao = rankingEntryDatabase[0].getEntryDao();

        if (NetCheckUtil.isNetConnection(mContext)) {
            Thread thread = new Thread(() -> {
                if (NetCheckUtil.isOnline()) {
                    RankingEntryReq rankingEntryReq = rankingNetGet.getRanking(type, version);

                    Log.d("version_id", version + "");
                    assert rankingEntryReq != null;
                    rankingEntries[0] = rankingEntryReq.getRankingEntry();
                    for (RankingEntry rankingEntry : rankingEntries[0]) {
                        assert rankingEntryDao != null;
                        rankingEntry.setVersionId(version);
                        Long id = rankingEntryDao.getIdByVersionAndRank(version, rankingEntry.getRank());
                        Log.d("entry_id", id+"");
                        if (id != null) {
                            rankingEntry.setId(id);
                            rankingEntryDao.updateEntry(rankingEntry);
                        } else rankingEntryDao.insertEntry(rankingEntry);
                    }
                } else {
                    Toast.makeText(mContext, "无网络链接", Toast.LENGTH_LONG).show();
                    assert rankingEntryDao != null;
                    rankingEntries[0] = rankingEntryDao.allEntryByVersion(version);
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "无网络链接", Toast.LENGTH_LONG).show();
            assert rankingEntryDao != null;
            rankingEntries[0] = rankingEntryDao.allEntryByVersion(version);
        }
        return rankingEntries[0];
    }

    public RankingEntry[] getRankingEntry(int type){
        RankingVersionDatabase rankingVersionDatabase = Room.databaseBuilder(mContext, RankingVersionDatabase.class, "ranking_version_database")
                .allowMainThreadQueries()
                .build();
        RankingVersionDao rankingVersionDao = rankingVersionDatabase.getVersionDao();

        assert rankingVersionDao != null;
        int version = rankingVersionDao.getVersionIdByDate(new Date());
        return this.getRankingEntry(type, version);
    }
}
