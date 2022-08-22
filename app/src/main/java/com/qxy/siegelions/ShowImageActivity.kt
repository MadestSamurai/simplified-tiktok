package com.qxy.siegelions

import android.view.View
import com.qxy.siegelions.base.BaseActivity
import kotlinx.android.synthetic.main.activity_show_image.*

/**
 * 图像展示页
 * @author MadSamurai
 */
class ShowImageActivity : BaseActivity() {

    override fun setLayoutId(): Int {
        return R.layout.activity_show_image
    }

    override fun init() {
        ivHead!!.setOnClickListener { v: View? -> finish() }
        val headRes = intent.getIntExtra("res", 0)
        ivHead!!.setImageResource(headRes)
    }
}