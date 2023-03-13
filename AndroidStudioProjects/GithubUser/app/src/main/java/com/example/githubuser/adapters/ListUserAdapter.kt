package com.example.githubuser.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.UserDetailsActivity
import com.example.githubuser.databinding.ItemRowUserBinding
import com.example.githubuser.networks.UserResponse

class ListUserAdapter(private val listUser: List<UserResponse>) :
    RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    /* Provider reference custom type of view */
    class ViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemRowUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(listUser[position].avatarUrl).into(holder.binding.ivAvatar)
        holder.binding.tvUsername.text = listUser[position].login.lowercase()
        holder.binding.itemRowUser.setOnClickListener {
            /* Start user detail activity */
            val intent = Intent(holder.itemView.context, UserDetailsActivity::class.java)
            intent.putExtra(
                UserDetailsActivity.EXTRA_USER,
                listUser[holder.adapterPosition].login.lowercase()
            )
            holder.itemView.context.startActivity(intent)
        }
    }
}