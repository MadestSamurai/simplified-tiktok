package com.qxy.siegelions.entity;

import com.qxy.siegelions.entity.RankingVersion

data class RankingVersionReq(
    var cursor: Int,
    var hasMore: Boolean?,
    var rankingVersion: RankingVersion
)
