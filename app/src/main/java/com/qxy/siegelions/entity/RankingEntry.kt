package com.qxy.siegelions.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "ranking_entry")
data class RankingEntry(
    @PrimaryKey
    var id: String,

    @ColumnInfo(name = "version_id")
    @SerializedName("version_id")
    var versionId: Long?,

    @ColumnInfo
    var actors: Array<String>?,

    @ColumnInfo
    var directors: Array<String>?,

    @ColumnInfo(name = "discussion_hot")
    @SerializedName("discussion_hot")
    var discussionHot: Long?,

    @ColumnInfo
    var hot: Long?,

    @ColumnInfo(name = "influence_hot")
    @SerializedName("influence_hot")
    var influenceHot: Long?,

    @ColumnInfo(name = "maoyan_id")
    @SerializedName("maoyan_id")
    var maoyanId: String?,

    @ColumnInfo(name = "name_cn")
    @SerializedName("name")
    var nameCN: String?,

    @ColumnInfo(name = "name_en")
    @SerializedName("name_en")
    var nameEN: String?,

    @ColumnInfo
    var poster: String?,

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    var releaseDate: Date?,

    @ColumnInfo(name = "search_hot")
    @SerializedName("search_hot")
    var searchHot: Long?,

    @ColumnInfo
    var tags: Array<String>?,

    @ColumnInfo(name = "topic_hot")
    @SerializedName("topic_hot")
    var topicHot: Long?,

    @ColumnInfo
    var type: Int,

    @ColumnInfo
    var rank: Int
)