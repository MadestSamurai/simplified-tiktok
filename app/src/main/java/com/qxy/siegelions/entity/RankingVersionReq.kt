package com.qxy.siegelions.entity;

/**
 * 排名版本JSON构造实体类
 * @author MadSamurai
 */
class RankingVersionReq(
    var cursor: Int,
    var hasMore: Boolean?,
    var rankingVersion: Array<RankingVersion>
)
