package com.qxy.siegelions.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * 排名版本行实体类
 * @author MadSamurai
 */
@Entity(tableName = "ranking_version")
data class RankingVersion(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,

    @ColumnInfo(name = "active_time")
    @SerializedName("active_time")
    var activeTime: Date?,

    @ColumnInfo(name = "end_time")
    @SerializedName("end_time")
    var endTime: Date?,

    @ColumnInfo(name = "start_time")
    @SerializedName("start_time")
    var startTime: Date?,

    @ColumnInfo
    var type: Int,

    @ColumnInfo
    var version: Int
)