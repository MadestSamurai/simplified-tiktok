package com.qxy.siegelions.ui

import androidx.recyclerview.widget.LinearLayoutManager
import com.qxy.siegelions.R
import com.qxy.siegelions.base.BaseFragment
import com.qxy.siegelions.entity.DataCreate
import kotlinx.android.synthetic.main.fragment_fans.*

/**
 * 粉丝碎片类
 * @author MadSamurai
 */
class FansFragment : BaseFragment() {
    private var fansAdapter: FansAdapter? = null

    override fun setLayoutId(): Int {
        return R.layout.fragment_fans
    }

    override fun init() {
        recyclerview!!.layoutManager = LinearLayoutManager(context)
        fansAdapter = FansAdapter(context, DataCreate.userList)
        recyclerview!!.adapter = fansAdapter
    }
}