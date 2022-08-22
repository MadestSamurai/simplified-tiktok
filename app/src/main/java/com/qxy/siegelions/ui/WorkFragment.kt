package com.qxy.siegelions.ui

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.qxy.siegelions.R
import com.qxy.siegelions.ui.WorkAdapter
import com.qxy.siegelions.base.BaseFragment
import com.qxy.siegelions.entity.DataCreate
import kotlinx.android.synthetic.main.fragment_work.*

/**
 * create by libo
 * create on 2020-05-19
 * description 个人作品fragment
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