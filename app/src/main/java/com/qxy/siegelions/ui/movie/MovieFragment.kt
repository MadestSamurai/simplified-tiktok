package com.qxy.siegelions.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.qxy.siegelions.R
import com.qxy.siegelions.database.RankingEntryDatabase
import com.qxy.siegelions.databinding.FragmentMovieBinding
import com.qxy.siegelions.ui.EntryAdapter

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

        val rankingEntryDatabase = context?.let {
            Room.databaseBuilder(
                it,
                RankingEntryDatabase::class.java, "ranking_entry_database") //new a database
                .allowMainThreadQueries()
                .build()
        }
        val rankingEntryDao = rankingEntryDatabase?.entryDao

        val entries = rankingEntryDao?.allEntry()
        entryAdapter = EntryAdapter(entries, context)
        listView.adapter = entryAdapter
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}