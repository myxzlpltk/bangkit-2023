package com.example.githubuser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ItemRowUserBinding
import com.example.githubuser.networks.UserResponse

class ListUserAdapter(private val listUser: List<UserResponse>) :
    RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    /* Provider reference custom type of view */
    class ViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(listUser[position].avatarUrl).into(holder.binding.ivAvatar)
        holder.binding.tvUsername.text = listUser[position].login
    }
}