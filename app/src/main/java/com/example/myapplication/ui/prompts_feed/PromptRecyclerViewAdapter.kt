package com.example.myapplication.ui.prompts_feed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.PromptRecyclerviewLayoutBinding

class PromptRecyclerViewAdapter(
    private val items: List<PromptModel>,
    private val itemOnClick: ItemOnClick
) :
    RecyclerView.Adapter<PromptRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: PromptRecyclerviewLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.prompt_recyclerview_layout,
            parent,
            false
        )
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind()


    override fun getItemCount() = items.size


    inner class ViewHolder(private val binding: PromptRecyclerviewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun onBind() {
            binding.prompts = items[adapterPosition]
            binding.saveButton.setOnClickListener(this)
            binding.writeButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.saveButton -> {
                    itemOnClick.savePrompt(adapterPosition)
                }
                R.id.writeButton -> {
                    itemOnClick.writeStory(adapterPosition)
                }
            }
        }
    }
}


interface ItemOnClick {
    fun writeStory(position: Int)
    fun savePrompt(position: Int)


}