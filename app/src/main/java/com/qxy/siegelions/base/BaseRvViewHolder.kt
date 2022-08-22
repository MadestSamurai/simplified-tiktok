package com.qxy.siegelions.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife

open class BaseRvViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
    init {
        ButterKnife.bind(this, itemView!!)
    }
}