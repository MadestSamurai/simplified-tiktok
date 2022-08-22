package com.qxy.siegelions

import com.qxy.siegelions.base.BaseActivity
import com.qxy.siegelions.ui.RecommendFragment

/**
 * 视频全屏播放页
 * @author MadSamurai
 */
class PlayListActivity : BaseActivity() {

    override fun setLayoutId(): Int {
        return R.layout.activity_play_list
    }

    override fun init() {
        supportFragmentManager.beginTransaction().add(R.id.framelayout, RecommendFragment()).commit()
    }

    companion object {
        @JvmField
        var initPos = 0
    }
}