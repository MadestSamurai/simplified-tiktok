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
import com.qxy.siegelions.entity.RankingVersion;
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

        findViewById(R.id.get_ranking).setOnClickListener(v -> new Thread() {
            @Override
            public void run() {
                RankingEntry[] rankingEntries = rankingGetUtil.getRanking(TYPE_MOVIE);
                for(RankingEntry rankingEntry : rankingEntries){
                    if(rankingEntryDao.getEntryById(rankingEntry.getId()) != null){
                        rankingEntryDao.updateEntry(rankingEntry);
                    }
                    else rankingEntryDao.insertEntry(rankingEntry);
                }
            }
        }.start());

        RankingVersionDatabase rankingVersionDatabase = Room.databaseBuilder(this,RankingVersionDatabase.class,"ranking_version_database")
                .allowMainThreadQueries()
                .build();
        RankingVersionDao rankingVersionDao = rankingVersionDatabase.getVersionDao();

        findViewById(R.id.get_ranking_version).setOnClickListener(v -> new Thread() {
            @Override
            public void run() {
                Boolean hasMore = true;
                while(hasMore) {
                    RankingVersion[] rankingVersions = rankingGetUtil.getRankingVersion(0, 15, TYPE_MOVIE);
                    for (RankingVersion rankingVersion : rankingVersions) {
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
