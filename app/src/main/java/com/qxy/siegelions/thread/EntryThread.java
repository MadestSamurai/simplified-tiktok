package com.qxy.siegelions.thread;

import android.content.Context;

import com.qxy.siegelions.entity.RankingEntry;
import com.qxy.siegelions.web.RankingNetGet;

import java.util.Objects;

public class EntryThread extends Thread {
    public RankingEntry[] rankingEntries;
    private final Context context;

    public EntryThread(Context context){
        this.context = context;
    }

    public void run(){
        RankingNetGet rankingNetGet = new RankingNetGet(context);
        rankingEntries = Objects.requireNonNull(rankingNetGet.getRanking(1)).getRankingEntry();
    }
}
