package com.example.myapplication.ui.authorization

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.UserRecyclerviewLayoutBinding

class UserRecyclerViewAdapter(
    private val items: List<UserModel>,
    private val itemOnClick: ItemOnClickUser
) :
    RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: UserRecyclerviewLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.user_recyclerview_layout,
            parent,
            false
        )
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind()


    override fun getItemCount() = items.size


    inner class ViewHolder(private val binding: UserRecyclerviewLayoutBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun onBind() {
            binding.userModel = items[adapterPosition]
            binding.discoverUserButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            itemOnClick.viewUserProfile(adapterPosition)
        }
    }
}


interface ItemOnClickUser {
    fun viewUserProfile(position: Int)
}