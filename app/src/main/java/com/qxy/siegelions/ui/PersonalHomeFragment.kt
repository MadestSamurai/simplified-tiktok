package com.qxy.siegelions.ui

import android.content.Intent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import com.qxy.siegelions.R
import com.qxy.siegelions.FocusActivity
import com.qxy.siegelions.ShowImageActivity
import com.qxy.siegelions.base.BaseFragment
import com.qxy.siegelions.base.CommPagerAdapter
import com.qxy.siegelions.entity.CurUser
import com.qxy.siegelions.entity.MainPageChangeEvent
import com.qxy.siegelions.util.NumUtils
import com.qxy.siegelions.util.RxBus
import com.google.android.material.appbar.AppBarLayout
import com.qxy.siegelions.entity.Video
import kotlinx.android.synthetic.main.fragment_personal_home.*
import kotlinx.android.synthetic.main.personal_home_header.*
import rx.Subscription
import rx.functions.Action1
import java.util.*

/**
 * create by libo
 * create on 2020-05-19
 * description 个人主页fragment
 */
class PersonalHomeFragment : BaseFragment(), View.OnClickListener {
    private val fragments = ArrayList<Fragment>()
    private var pagerAdapter: CommPagerAdapter? = null
    private var curUserBean: Video.User? = null
    private var subscription: Subscription? = null

    override fun setLayoutId(): Int {
        return R.layout.fragment_personal_home
    }

    override fun init() {

        //解决toolbar左边距问题
        toolbar!!.setContentInsetsAbsolute(0, 0)
        setappbarlayoutPercent()
        ivReturn!!.setOnClickListener(this)
        ivHead!!.setOnClickListener(this)
        ivBg!!.setOnClickListener(this)
        llFocus!!.setOnClickListener(this)
        llFans!!.setOnClickListener(this)
        setUserInfo()
    }

    /**
     * 过渡动画跳转页面
     *
     * @param view
     */
    fun transitionAnim(view: View?, res: Int) {
        val compat = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), requireView(), getString(R.string.trans))
        val intent = Intent(activity, ShowImageActivity::class.java)
        intent.putExtra("res", res)
        ActivityCompat.startActivity(requireActivity(), intent, compat.toBundle())
    }

    fun setUserInfo() {
        subscription = RxBus.getDefault().toObservable(CurUser::class.java).subscribe(Action1 { curUser: CurUser ->
            coordinatorLayoutBackTop()
            this.curUserBean = curUser.user
            ivBg!!.setImageResource(curUser.user.head)
            ivHead!!.setImageResource(curUser.user.head)
            tvNickname!!.text = curUser.user.nickName
            tvSign!!.text = curUser.user.sign
            tvTitle!!.text = curUser.user.nickName
            val subCount = NumUtils.numberFilter(curUser.user.subCount)
            val focusCount = NumUtils.numberFilter(curUser.user.focusCount)
            val fansCount = NumUtils.numberFilter(curUser.user.fansCount)

            //获赞 关注 粉丝
            tvGetLikeCount!!.text = subCount
            tvFocusCount!!.text = focusCount
            tvFansCount!!.text = fansCount

            //关注状态
            if (curUser.user.isFocused) {
                tvAddfocus!!.text = "已关注"
                tvAddfocus!!.setBackgroundResource(R.drawable.shape_round_halfwhite)
            } else {
                tvAddfocus!!.text = "关注"
                tvAddfocus!!.setBackgroundResource(R.drawable.shape_round_red)
            }
            setTabLayout()
        } as Action1<CurUser>)
    }

    private fun setTabLayout() {
        val titles = arrayOf("作品 " + curUserBean!!.workCount, "动态 " + curUserBean!!.dynamicCount, "喜欢 " + curUserBean!!.likeCount)
        fragments.clear()
        for (i in titles.indices) {
            fragments.add(WorkFragment())
            tabLayout!!.addTab(tabLayout!!.newTab().setText(titles[i]))
        }
        pagerAdapter = CommPagerAdapter(childFragmentManager, fragments, titles)
        viewPager!!.adapter = pagerAdapter
        tabLayout!!.setupWithViewPager(viewPager)
    }

    /**
     * 根据滚动比例渐变view
     */
    private fun setappbarlayoutPercent() {
        appbarlayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appbarlayout, verticalOffset ->
            val percent = Math.abs(verticalOffset * 1.0f) / appbarlayout.totalScrollRange //滑动比例
            if (percent > 0.8) {
                tvTitle!!.visibility = View.VISIBLE
                tvFocus!!.visibility = View.VISIBLE
                val alpha = 1 - (1 - percent) * 5 //渐变变换
                tvTitle!!.alpha = alpha
                tvFocus!!.alpha = alpha
            } else {
                tvTitle!!.visibility = View.GONE
                tvFocus!!.visibility = View.GONE
            }
        })
    }

    /**
     * 自动回顶部
     */
    private fun coordinatorLayoutBackTop() {
        val behavior = (appbarlayout!!.layoutParams as CoordinatorLayout.LayoutParams).behavior
        if (behavior is AppBarLayout.Behavior) {
            val appbarlayoutBehavior = behavior
            val topAndBottomOffset = appbarlayoutBehavior.topAndBottomOffset
            if (topAndBottomOffset != 0) {
                appbarlayoutBehavior.topAndBottomOffset = 0
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ivReturn -> RxBus.getDefault().post(MainPageChangeEvent(0))
            R.id.ivHead -> transitionAnim(ivHead, curUserBean!!.head)
            R.id.ivBg -> {
            }
            R.id.llFocus -> startActivity(Intent(activity, FocusActivity::class.java))
            R.id.llFans -> startActivity(Intent(activity, FocusActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (subscription != null) {
            subscription!!.unsubscribe()
        }
    }
}