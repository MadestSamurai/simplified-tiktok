package com.qxy.siegelions.ui.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.qxy.siegelions.R
import com.qxy.siegelions.database.RankingEntryDatabase
import com.qxy.siegelions.database.RankingVersionDatabase
import com.qxy.siegelions.databinding.FragmentMovieBinding
import com.qxy.siegelions.ui.EntryAdapter
import java.util.*

class MovieFragment : Fragment() {
    private var binding: FragmentMovieBinding? = null
    private lateinit var listView: ListView
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

        view?.findViewById<Button?>(R.id.date_btn)
            ?.setOnClickListener { v: View -> buttonDateClicked(v) }

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

        val date = Date();

        val version = rankingVersionDao?.getVersionIdByDate(date)

        val entries = version?.let { rankingEntryDao?.allEntryByVersion(it) }
        entryAdapter = EntryAdapter(entries, context)
        listView.adapter = entryAdapter

        return view
    }


    private fun buttonDateClicked(v:View){
        Log.d("Date_button_test", "Pressed")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}