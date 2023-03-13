package com.example.githubuser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.databinding.ItemRowUserBinding
import com.example.githubuser.networks.UserResponse

class ListFollowAdapter(private val listFollow: List<UserResponse>) :
    RecyclerView.Adapter<ListFollowAdapter.ViewHolder>() {

    /* Provider reference custom type of view */
    class ViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = listFollow.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}