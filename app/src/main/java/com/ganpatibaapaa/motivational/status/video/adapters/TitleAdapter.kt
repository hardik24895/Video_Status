package com.ganpatibaapaa.motivational.status.video.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ganpatibaapaa.motivational.status.video.R
import com.ganpatibaapaa.motivational.status.video.activities.ViewAllActivity

class TitleAdapter(private val list: ArrayList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        context = parent.context
        return TitleCardViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.video_thumb_card_view,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val titleCardViewHolder = holder as TitleCardViewHolder
        Glide.with(context)
            .load(list[position])
            .into(titleCardViewHolder.imageView)
        titleCardViewHolder.cardView.setOnClickListener{
            context.startActivity(Intent(context, ViewAllActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class TitleCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView = view.findViewById<ImageView>(R.id.titleItemImageView)!!
        var cardView = view.findViewById<CardView>(R.id.titleItemCardView)!!
    }
}