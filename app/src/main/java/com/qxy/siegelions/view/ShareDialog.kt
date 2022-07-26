package com.qxy.siegelions.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.qxy.siegelions.R
import com.qxy.siegelions.ui.PrivateLetterAdapter
import com.qxy.siegelions.ui.ShareAdapter
import com.qxy.siegelions.entity.DataCreate
import com.qxy.siegelions.entity.Share
import kotlinx.android.synthetic.main.dialog_share.*
import java.util.*

/**
 * 分享弹框
 * @author MadSamurai
 */
class ShareDialog : BaseBottomSheetDialog() {

    private var privateLetterAdapter: PrivateLetterAdapter? = null
    private var shareAdapter: ShareAdapter? = null
    private val shares = ArrayList<Share>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.dialog_share, container)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        rvPrivateLetter!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        privateLetterAdapter = PrivateLetterAdapter(context, DataCreate.userList)
        rvPrivateLetter!!.adapter = privateLetterAdapter
        rvShare!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        shareAdapter = ShareAdapter(context, shares)
        rvShare!!.adapter = shareAdapter
        setShareDatas()
    }

    private fun setShareDatas() {
        shares.add(Share(R.string.icon_friends, "朋友圈", R.color.color_wechat_iconbg))
        shares.add(Share(R.string.icon_wechat, "微信", R.color.color_wechat_iconbg))
        shares.add(Share(R.string.icon_qq, "QQ", R.color.color_qq_iconbg))
        shares.add(Share(R.string.icon_qq_space, "QQ空间", R.color.color_qqzone_iconbg))
        shares.add(Share(R.string.icon_weibo, "微博", R.color.color_weibo_iconbg))
        shares.add(Share(R.string.icon_comment, "私信好友", R.color.color_FF0041))
        shareAdapter!!.notifyDataSetChanged()
    }

    protected override val height: Int
        protected get() = dp2px(requireContext(), 355f)
}