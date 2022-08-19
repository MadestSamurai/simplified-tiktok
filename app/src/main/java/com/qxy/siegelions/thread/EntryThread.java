package com.qxy.siegelions.thread;

import android.content.Context;

import com.qxy.siegelions.entity.RankingEntry;
import com.qxy.siegelions.util.RankingGetUtil;

import java.util.Objects;

public class EntryThread extends Thread {
    public RankingEntry[] rankingEntries;
    private final Context context;

    public EntryThread(Context context){
        this.context = context;
    }

    public void run(){
        RankingGetUtil rankingGetUtil = new RankingGetUtil(context);
        rankingEntries = Objects.requireNonNull(rankingGetUtil.getRanking(1)).getRankingEntry();
    }
}
