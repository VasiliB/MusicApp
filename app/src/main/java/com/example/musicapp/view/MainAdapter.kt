package com.example.musicapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapp.R
import com.example.musicapp.model.Track
import kotlinx.android.synthetic.main.item_layout.view.*

class MainAdapter(
    private val tracks: ArrayList<Track>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(track: Track) {
            itemView.textViewUserName.text = track.artist
            itemView.textViewUserEmail.text = track.title
            Glide.with(itemView.imageViewAvatar.context)
                .load(track.bitmapUri)
                .into(itemView.imageViewAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = tracks.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(tracks[position])

    fun addData(list: List<Track>) {
        tracks.addAll(list)
    }
}