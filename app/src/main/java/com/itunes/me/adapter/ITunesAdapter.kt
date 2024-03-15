package com.itunes.me.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.itunes.me.R
import com.itunes.me.model.Result

class ITunesAdapter : BaseQuickAdapter<Result, QuickViewHolder>() {
    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: Result?) {
        Glide.with(holder.itemView)
            .load(item?.artworkUrl100)
            .centerCrop()
            .placeholder(R.drawable.loading)
            .error(R.drawable.load_failed)
            .into(holder.getView(R.id.image))

        holder.getView<TextView>(R.id.track_name).text = item?.trackName
        holder.getView<TextView>(R.id.artist_name).text = item?.artistName
        holder.getView<TextView>(R.id.price).text = "Price: $${item?.trackPrice}"
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.itunes_item, parent)
    }
}