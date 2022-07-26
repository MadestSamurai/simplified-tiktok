package com.qxy.siegelions.ui

import android.content.Intent
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.qxy.siegelions.MenuActivity
import com.qxy.siegelions.R
import com.qxy.siegelions.base.BaseFragment
import com.qxy.siegelions.base.CommPagerAdapter
import com.qxy.siegelions.entity.PauseVideoEvent
import com.qxy.siegelions.util.RxBus
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*

/**
 * 主页碎片类
 * @author MadSamurai
 */
class MainFragment : BaseFragment() {
    private var currentLocationFragment: CurrentLocationFragment? = null
    private var recommendFragment: RecommendFragment? = null

    private val fragments = ArrayList<Fragment>()
    private var pagerAdapter: CommPagerAdapter? = null

    override fun setLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun init() {
        setFragments()
        setMainMenu()
    }

    private fun setFragments() {
        currentLocationFragment = CurrentLocationFragment()
        recommendFragment = RecommendFragment()
        fragments.add(currentLocationFragment!!)
        fragments.add(recommendFragment!!)
        tabTitle!!.addTab(tabTitle!!.newTab().setText("南京"))
        tabTitle!!.addTab(tabTitle!!.newTab().setText("推荐"))
        pagerAdapter = CommPagerAdapter(childFragmentManager, fragments, arrayOf("南京", "推荐"))
        viewPager!!.adapter = pagerAdapter
        tabTitle!!.setupWithViewPager(viewPager)
        tabTitle!!.getTabAt(1)!!.select()
        viewPager!!.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                curPage = position
                if (position == 1) {
                    //继续播放
                    RxBus.getDefault().post(PauseVideoEvent(true))
                } else {
                    //切换到其他页面，需要暂停视频
                    RxBus.getDefault().post(PauseVideoEvent(false))
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun setMainMenu() {
        tabMainMenu!!.addTab(tabMainMenu!!.newTab().setText("首页"))
        tabMainMenu!!.addTab(tabMainMenu!!.newTab().setText("好友"))
        tabMainMenu!!.addTab(tabMainMenu!!.newTab().setText(""))
        tabMainMenu!!.addTab(tabMainMenu!!.newTab().setText("排行榜"))
        tabMainMenu!!.addTab(tabMainMenu!!.newTab().setText("我"))
    }

    companion object {
        /** 默认显示第一页推荐页  */
        var curPage = 1
    }
}