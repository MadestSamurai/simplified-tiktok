package com.qxy.siegelions.ui.movie

import android.graphics.Color
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import com.qxy.siegelions.databinding.FragmentMovieBinding
import com.qxy.siegelions.ui.EntryAdapter
import com.qxy.siegelions.util.RankingGetUtil
import java.util.*

class MovieFragment : Fragment() {
    private var binding: FragmentMovieBinding? = null
    private lateinit var listView: ListView
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var showPrev: ImageButton
    private lateinit var showNext: ImageButton
    private var entryAdapter: EntryAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        ViewModelProvider(this).get(
            MovieViewModel::class.java
        )

        binding = FragmentMovieBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        val view = inflater.inflate(R.layout.fragment_movie, container, false)
        listView = view.findViewById(R.id.list_view)

        val rankingEntryDatabase = context?.let {
            Room.databaseBuilder(
                it,
                RankingEntryDatabase::class.java, "ranking_entry_database"
            ) //new a database
                .allowMainThreadQueries()
                .build()
        }
        val rankingEntryDao = rankingEntryDatabase?.entryDao

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
            val rankingGetUtil = RankingGetUtil(context)
            val versionView = view.findViewById(R.id.version_id) as TextView
            val version = versionView.text.toString().toInt()
            val entries = rankingGetUtil.getRankingEntry(1, version)
            //在获取数据后重新调用适配器设置数据显示
            entryAdapter = EntryAdapter(entries, context)
            listView.adapter = entryAdapter
            //在适配器类中设置数据完毕时，关闭动画
            refreshLayout.isRefreshing = false //关闭刷新动画
        })

        showPrev = view.findViewById(R.id.show_prev)
        showNext = view.findViewById(R.id.show_next)

        showPrev.setOnClickListener(View.OnClickListener {
            val rankingGetUtil = RankingGetUtil(context)
            val versionView = view.findViewById(R.id.version_id) as TextView
            val version = versionView.text.toString().toInt()
            val entries = rankingGetUtil.getRankingEntry(1, version - 1)
            versionView.text = (version-1).toString()
            //在获取数据后重新调用适配器设置数据显示
            entryAdapter = EntryAdapter(entries, context)
            listView.adapter = entryAdapter
        })

        showNext.setOnClickListener(View.OnClickListener {
            val rankingGetUtil = RankingGetUtil(context)
            val versionView = view.findViewById(R.id.version_id) as TextView
            val version = versionView.text.toString().toInt()
            val entries = rankingGetUtil.getRankingEntry(1, version+1)
            versionView.text = (version-1).toString()
            //在获取数据后重新调用适配器设置数据显示
            entryAdapter = EntryAdapter(entries, context)
            listView.adapter = entryAdapter
        })

        val date = Date();
        val version = rankingVersionDao?.getVersionIdByDate(date)

        val entries = version?.let { rankingEntryDao?.allEntryByVersion(it) }
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