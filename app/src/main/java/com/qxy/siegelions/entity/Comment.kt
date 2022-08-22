package com.qxy.siegelions.entity

import com.qxy.siegelions.entity.Video.User

/**
 * 评论实体类
 * @author MadSamurai
 */
class Comment {
    var content: String? = null
        get() = if (field == null) "" else field
    var user: User? = null
    var likeCount = 0
    var isLiked = false
}