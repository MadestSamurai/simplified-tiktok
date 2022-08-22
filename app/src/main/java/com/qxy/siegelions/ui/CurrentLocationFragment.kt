package com.qxy.siegelions.ui

import android.os.CountDownTimer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.qxy.siegelions.R
import com.qxy.siegelions.base.BaseFragment
import com.qxy.siegelions.entity.DataCreate
import kotlinx.android.synthetic.main.fragment_current_location.*

/**
 * 当前浏览位置碎片类
 * @author MadSamurai
 */
class CurrentLocationFragment : BaseFragment() {
    private var adapter: GridVideoAdapter? = null

    override fun setLayoutId(): Int {
        return R.layout.fragment_current_location
    }

    override fun init() {
        DataCreate().initData()
        recyclerView!!.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter = GridVideoAdapter(activity, DataCreate.datas)
        recyclerView!!.adapter = adapter
        refreshLayout!!.setColorSchemeResources(R.color.color_link)
        refreshLayout!!.setOnRefreshListener {
            object : CountDownTimer(1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() {
                    refreshLayout!!.isRefreshing = false
                }
            }.start()
        }
    }
}