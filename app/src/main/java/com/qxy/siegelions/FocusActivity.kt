package com.qxy.siegelions

import androidx.fragment.app.Fragment
import com.qxy.siegelions.base.BaseActivity
import com.qxy.siegelions.base.CommPagerAdapter
import com.qxy.siegelions.ui.FansFragment
import kotlinx.android.synthetic.main.activity_focus.*
import java.util.*

/**
 * create by libo
 * create on 2020-05-14
 * description 粉丝关注人页面
 */
class FocusActivity : BaseActivity() {

    private val fragments = ArrayList<Fragment>()
    private var pagerAdapter: CommPagerAdapter? = null
    private val titles = arrayOf("关注 128", "粉丝 128", "推荐关注")

    override fun setLayoutId(): Int {
        return R.layout.activity_focus
    }

    override fun init() {
        for (i in titles.indices) {
            fragments.add(FansFragment())
            tablayout!!.addTab(tablayout!!.newTab().setText(titles[i]))
        }
        pagerAdapter = CommPagerAdapter(supportFragmentManager, fragments, titles)
        viewpager!!.adapter = pagerAdapter
        tablayout!!.setupWithViewPager(viewpager)
    }
}