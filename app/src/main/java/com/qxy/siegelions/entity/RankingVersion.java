package com.qxy.siegelions.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "ranking_version")
public class RankingVersion {
    @PrimaryKey(autoGenerate = true)
    Long id;

    @ColumnInfo(name = "active_name")
    @SerializedName("active_name")
    Date activeTime;

    @ColumnInfo(name = "end_time")
    @SerializedName("end_time")
    Date endTime;

    @ColumnInfo(name = "start_time")
    @SerializedName("start_time")
    Date startTime;

    @ColumnInfo
    int type;

    @ColumnInfo
    int version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
