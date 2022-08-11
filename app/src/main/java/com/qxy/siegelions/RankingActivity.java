package com.qxy.siegelions;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.qxy.siegelions.entity.RankingEntry;
import com.qxy.siegelions.entity.RankingVersion;
import com.qxy.siegelions.util.RankingGetUtil;

public class RankingActivity extends AppCompatActivity {
    private final int TYPE_MOVIE = 1;
    private final int TYPE_TELEPLAY = 2;
    private final int TYPE_VARIETY_SHOW = 3;

    private final RankingGetUtil rankingGetUtil = new RankingGetUtil(RankingActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread() {
            @Override
            public void run() {
                RankingEntry[] rankingEntryArray = rankingGetUtil.getRanking(TYPE_MOVIE);
                Log.d("siegeLions", "EntryTest: " + rankingEntryArray[0].getActors()[0]);
                Log.d("siegeLions", "EntryCount: " + rankingEntryArray.length);
            }
        }.start();

        findViewById(R.id.get_ranking_version).setOnClickListener(v -> new Thread() {
            @Override
            public void run() {
                RankingVersion[] rankingVersionArray = rankingGetUtil.getRankingVersion(0, 15, TYPE_MOVIE);
                Log.d("siegeLions", "VersionTest: " + rankingVersionArray[0].getVersion());
            }
        }.start());
    }
}
