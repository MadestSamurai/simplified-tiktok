package com.qxy.siegelions.entity

import com.qxy.siegelions.entity.Video.User

/**
 * create by libo
 * create on 2020-06-04
 * description
 */
class CommentBean {
    var content: String? = null
        get() = if (field == null) "" else field
    var user: User? = null
    var likeCount = 0
    var isLiked = false
}