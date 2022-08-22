package com.qxy.siegelions.ui.show

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.qxy.siegelions.R
import com.qxy.siegelions.database.RankingEntryDatabase
import com.qxy.siegelions.database.RankingVersionDatabase
import com.qxy.siegelions.databinding.FragmentShowBinding
import com.qxy.siegelions.ui.EntryAdapter
import com.qxy.siegelions.util.RankingGetUtil
import com.qxy.siegelions.util.RankingVersionGetUtil
import java.text.SimpleDateFormat
import java.util.*

/**
 * 综艺数据页碎片类
 * @author akkcdb110
 */
class ShowFragment : Fragment() {
    private var binding: FragmentShowBinding? = null
    private lateinit var listView: ListView
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var showPrev: ImageButton
    private lateinit var showNext: ImageButton
    private lateinit var refreshTime: TextView
    private var entryAdapter: EntryAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        ViewModelProvider(this).get(
            ShowViewModel::class.java
        )

        binding = FragmentShowBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        val view = inflater.inflate(R.layout.fragment_show, container, false)
        listView = view.findViewById(R.id.list_view)

        val rankingVersionGetUtil = RankingVersionGetUtil(context)
        rankingVersionGetUtil.saveRankingVersion(3)

        val rankingEntryDatabase = context?.let {
            Room.databaseBuilder(
                it,
                RankingEntryDatabase::class.java, "ranking_entry_database"
            ) //new a database
                .allowMainThreadQueries()
                .build()
        }
        val rankingEntryDao = rankingEntryDatabase?.entryDao

        val fmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
        refreshTime = view.findViewById(R.id.info_refresh_date)
        refreshTime.text = "$fmt 更新"

        val rankingVersionDatabase = context?.let {
            Room.databaseBuilder(
                it,
                RankingVersionDatabase::class.java, "ranking_version_database"
            ) //new a database
                .allowMainThreadQueries()
                .build()
        }
        val rankingVersionDao = rankingVersionDatabase?.versionDao

        refreshLayout = view.findViewById(R.id.refresh_layout)
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.LTGRAY) //刷新控件的背景

        refreshLayout.setOnRefreshListener(OnRefreshListener {
            val rankingGetUtil = context?.let { RankingGetUtil(it) }
            val versionView = view.findViewById(R.id.version_id) as TextView
            val version = versionView.text.toString().toInt()
            val entries = rankingGetUtil?.getRankingEntry(3, version)
            val fmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
            refreshTime = view.findViewById(R.id.info_refresh_date)
            refreshTime.text = "$fmt 更新"
            //在获取数据后重新调用适配器设置数据显示
            entryAdapter = EntryAdapter(entries, context)
            listView.adapter = entryAdapter
            //在适配器类中设置数据完毕时，关闭动画
            refreshLayout.isRefreshing = false //关闭刷新动画
        })

        showPrev = view.findViewById(R.id.show_prev)
        showNext = view.findViewById(R.id.show_next)

        showPrev.setOnClickListener(View.OnClickListener {
            val rankingGetUtil = context?.let { it1 -> RankingGetUtil(it1) }
            val versionView = view.findViewById(R.id.version_id) as TextView
            val version = versionView.text.toString().toInt()
            val entries = rankingGetUtil?.getRankingEntry(3, version - 1)
            versionView.text = (version - 1).toString()
            //在获取数据后重新调用适配器设置数据显示
            entryAdapter = EntryAdapter(entries, context)
            listView.adapter = entryAdapter
        })

        showNext.setOnClickListener(View.OnClickListener {
            val rankingGetUtil = context?.let { it1 -> RankingGetUtil(it1) }
            val versionView = view.findViewById(R.id.version_id) as TextView
            val version = versionView.text.toString().toInt()
            val entries = rankingGetUtil?.getRankingEntry(3, version + 1)
            versionView.text = (version - 1).toString()
            //在获取数据后重新调用适配器设置数据显示
            entryAdapter = EntryAdapter(entries, context)
            listView.adapter = entryAdapter
        })

        val rankingGetUtil = context?.let { RankingGetUtil(it) }
        val entries = rankingGetUtil?.getRankingEntry(3)

        entryAdapter = EntryAdapter(entries, context)
        listView.adapter = entryAdapter

        return view
    }

    private fun buttonDateClicked(v: View) {
        Log.d("Date_button_test", "Pressed")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}