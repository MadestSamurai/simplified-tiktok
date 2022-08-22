package com.qxy.siegelions.ui

import androidx.recyclerview.widget.GridLayoutManager
import com.qxy.siegelions.R
import com.qxy.siegelions.base.BaseFragment
import com.qxy.siegelions.entity.DataCreate
import kotlinx.android.synthetic.main.fragment_work.*

/**
 * 工作碎片类
 * @author MadSamurai
 */
class WorkFragment : BaseFragment() {
    private var workAdapter: WorkAdapter? = null

    override fun setLayoutId(): Int {
        return R.layout.fragment_work
    }

    override fun init() {
        recyclerview!!.layoutManager = GridLayoutManager(activity, 3)
        workAdapter = WorkAdapter(activity, DataCreate.datas)
        recyclerview!!.adapter = workAdapter
    }
}