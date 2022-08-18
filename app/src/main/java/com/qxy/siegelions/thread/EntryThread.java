package com.qxy.siegelions.thread;

import android.content.Context;

import com.qxy.siegelions.entity.RankingEntry;
import com.qxy.siegelions.util.RankingGetUtil;

public class EntryThread extends Thread {
    public RankingEntry[] rankingEntries;
    private final Context context;

    public EntryThread(Context context){
        this.context = context;
    }

    public void run(){
        RankingGetUtil rankingGetUtil = new RankingGetUtil(context);
        rankingEntries = rankingGetUtil.getRanking(1);
    }
}
