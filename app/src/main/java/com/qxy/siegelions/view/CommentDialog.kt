package com.qxy.siegelions.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.qxy.siegelions.R
import com.qxy.siegelions.ui.CommentAdapter
import com.qxy.siegelions.entity.Comment
import com.qxy.siegelions.entity.DataCreate
import kotlinx.android.synthetic.main.dialog_comment.*
import java.util.*

/**
 * 评论弹框
 * @author MadSamurai
 */
class CommentDialog : BaseBottomSheetDialog() {

    private var commentAdapter: CommentAdapter? = null
    private val datas = ArrayList<Comment>()
    private val likeArray = intArrayOf(4919, 334, 121, 423, 221, 23)
    private val commentArray = arrayOf("我就说左脚踩右脚可以上天你们还不信！", "全是评论点赞，没人关注吗", "哈哈哈哈", "像谁，没看出来", "你这西安话真好听")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.dialog_comment, container)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        commentAdapter = CommentAdapter(context, datas)
        recyclerView!!.adapter = commentAdapter
        loadData()
    }

    private fun loadData() {
        for (i in DataCreate.userList.indices) {
            val comment = Comment()
            comment.user = DataCreate.userList[i]
            comment.content = commentArray[(Math.random() * commentArray.size).toInt()]
            comment.likeCount = likeArray[(Math.random() * likeArray.size).toInt()]
            datas.add(comment)
        }
        commentAdapter!!.notifyDataSetChanged()
    }

    protected override val height: Int
        protected get() = resources.displayMetrics.heightPixels - 600
}