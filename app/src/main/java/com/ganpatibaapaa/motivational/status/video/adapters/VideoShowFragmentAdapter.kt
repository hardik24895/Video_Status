package com.ganpatibaapaa.motivational.status.video.adapters

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.GanpatiBappiMoriya.motivational.status.video.fragment.VideoShowFragment
import com.ganpatibaapaa.motivational.status.video.model.Video

class VideoShowFragmentAdapter(activity: AppCompatActivity, private val list: ArrayList<Video>): FragmentStateAdapter(activity) {
    var ADINDEX = 3
    var OFFSET = 4
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        if (position == ADINDEX) {
            OFFSET += 2
            ADINDEX += OFFSET
            return VideoShowFragment.newInstance(
                position,
                list[position].URL,
                list[position].ThumbURL,
                false
            )
        }
        return VideoShowFragment.newInstance(
            position,
            list[position].URL,
            list[position].ThumbURL,
            true
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(newList: ArrayList<Video>) {
        list?.addAll(newList)
        notifyDataSetChanged()
    }

}