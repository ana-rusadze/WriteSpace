package com.example.myapplication.ui.stories_feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.StoryRecyclerviewLayoutBinding

class StoryRecyclerViewAdapter(
    private val items: List<StoryModel>,
    private val itemOnClick: ItemOnClick
) :
    RecyclerView.Adapter<StoryRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: StoryRecyclerviewLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.story_recyclerview_layout,
            parent,
            false
        )
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind()


    override fun getItemCount() = items.size


    inner class ViewHolder(private val binding: StoryRecyclerviewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun onBind() {
            binding.storyModel = items[adapterPosition]
//            binding.saveButton.setOnClickListener(this)
//            binding.writeButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
//            when (v?.id) {
//                R.id.saveButton -> {
//                    itemOnClick.savePrompt(adapterPosition)
//                }
//                R.id.writeButton -> {
//                    itemOnClick.writeStory(adapterPosition)
//                }
//            }
        }
    }
}


interface ItemOnClick {
    fun writeStory(position: Int)
    fun savePrompt(position: Int)


}