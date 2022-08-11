package com.qxy.siegelions.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "ranking_entry")
public class RankingEntry {

    @PrimaryKey
    String id;

    @ColumnInfo(name = "version_id")
    @SerializedName("version_id")
    Long versionId;

    @ColumnInfo
    String[] actors;

    @ColumnInfo
    String[] directors;

    @ColumnInfo(name = "discussion_hot")
    @SerializedName("discussion_hot")
    Long discussionHot;

    @ColumnInfo
    Long hot;

    @ColumnInfo(name = "influence_hot")
    @SerializedName("influence_hot")
    Long influenceHot;

    @ColumnInfo(name = "maoyan_id")
    @SerializedName("maoyan_id")
    String maoyanId;

    @ColumnInfo(name = "name_cn")
    @SerializedName("name")
    String nameCN;

    @ColumnInfo(name = "name_en")
    @SerializedName("name_en")
    String nameEN;

    @ColumnInfo
    String poster;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    Date releaseDate;

    @ColumnInfo(name = "search_hot")
    @SerializedName("search_hot")
    Long searchHot;

    @ColumnInfo(name = "topic_hot")
    @SerializedName("topic_hot")
    Long topicHot;

    @ColumnInfo
    int type;

    @ColumnInfo
    int rank;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getActors() {
        return actors;
    }

    public void setActors(String[] actors) {
        this.actors = actors;
    }

    public String[] getDirectors() {
        return directors;
    }

    public void setDirectors(String[] directors) {
        this.directors = directors;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public Long getDiscussionHot() {
        return discussionHot;
    }

    public void setDiscussionHot(Long discussionHot) {
        this.discussionHot = discussionHot;
    }

    public Long getHot() {
        return hot;
    }

    public void setHot(Long hot) {
        this.hot = hot;
    }

    public Long getInfluenceHot() {
        return influenceHot;
    }

    public void setInfluenceHot(Long influenceHot) {
        this.influenceHot = influenceHot;
    }

    public String getMaoyanId() {
        return maoyanId;
    }

    public void setMaoyanId(String maoyanId) {
        this.maoyanId = maoyanId;
    }

    public String getNameCN() {
        return nameCN;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setNameCN(String nameCN) {
        this.nameCN = nameCN;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Long getSearchHot() {
        return searchHot;
    }

    public void setSearchHot(Long searchHot) {
        this.searchHot = searchHot;
    }

    public Long getTopicHot() {
        return topicHot;
    }

    public void setTopicHot(Long topicHot) {
        this.topicHot = topicHot;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
