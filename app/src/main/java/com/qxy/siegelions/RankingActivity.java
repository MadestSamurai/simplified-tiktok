package com.qxy.siegelions;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.qxy.siegelions.dao.RankingEntryDao;
import com.qxy.siegelions.dao.RankingVersionDao;
import com.qxy.siegelions.database.RankingEntryDatabase;
import com.qxy.siegelions.database.RankingVersionDatabase;
import com.qxy.siegelions.entity.RankingEntry;
import com.qxy.siegelions.entity.RankingEntryReq;
import com.qxy.siegelions.entity.RankingVersion;
import com.qxy.siegelions.entity.RankingVersionReq;
import com.qxy.siegelions.util.RankingGetUtil;

import java.util.Objects;

public class RankingActivity extends AppCompatActivity {
    private final int TYPE_MOVIE = 1;
    private final int TYPE_TELEPLAY = 2;
    private final int TYPE_VARIETY_SHOW = 3;

    private final RankingGetUtil rankingGetUtil = new RankingGetUtil(RankingActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        RankingEntryDatabase rankingEntryDatabase = Room.databaseBuilder(this,RankingEntryDatabase.class,"ranking_entry_database")
                .allowMainThreadQueries()
                .build();
        RankingEntryDao rankingEntryDao = rankingEntryDatabase.getEntryDao();

        RankingVersionDatabase rankingVersionDatabase = Room.databaseBuilder(this,RankingVersionDatabase.class,"ranking_version_database")
                .allowMainThreadQueries()
                .build();
        RankingVersionDao rankingVersionDao = rankingVersionDatabase.getVersionDao();

        findViewById(R.id.get_ranking).setOnClickListener(v -> new Thread() {
            @Override
            public void run() {
                RankingEntryReq rankingEntryReq = rankingGetUtil.getRanking(TYPE_MOVIE);
                assert rankingEntryReq != null;
                assert rankingVersionDao != null;
                long version = Objects.requireNonNull(rankingVersionDao.getVersionByDate(Objects.requireNonNull(rankingEntryReq.getActiveTime()))).getVersion();
                for(RankingEntry rankingEntry : rankingEntryReq.getRankingEntry()){
                    assert rankingEntryDao != null;
                    rankingEntry.setVersionId(version);
                    if(rankingEntryDao.getEntryById(rankingEntry.getId()) != null){
                        rankingEntryDao.updateEntry(rankingEntry);
                    }
                    else rankingEntryDao.insertEntry(rankingEntry);
                }
            }
        }.start());

        findViewById(R.id.get_ranking_version).setOnClickListener(v -> new Thread() {
            @Override
            public void run() {
                Boolean hasMore = true;
                int cursor = 0;
                while(Boolean.TRUE.equals(hasMore)) {
                    RankingVersionReq rankingVersionReq = rankingGetUtil.getRankingVersion(cursor, 15, TYPE_MOVIE);
                    cursor = rankingVersionReq.getCursor();
                    hasMore = rankingVersionReq.getHasMore();
                    for (RankingVersion rankingVersion : rankingVersionReq.getRankingVersion()) {
                        assert rankingVersionDao != null;
                        if (rankingVersionDao.getVersionByDate(Objects.requireNonNull(rankingVersion.getActiveTime())) != null) {
                            rankingVersionDao.updateVersion(rankingVersion);
                        } else rankingVersionDao.insertVersion(rankingVersion);
                    }
                }
            }
        }.start());

        findViewById(R.id.look_ranking).setOnClickListener(v -> {
            Intent intent = new Intent(RankingActivity.this , MenuActivity.class);
            startActivity(intent);
        });
    }


}
