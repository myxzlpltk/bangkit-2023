package com.example.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.local.entity.UserEntity
import com.example.githubuser.databinding.ItemRowUserBinding
import com.example.githubuser.helper.UserDiffCallback
import com.example.githubuser.ui.detail.UserDetailsActivity

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    // List of users
    private var listUser = ArrayList<UserEntity>()

    /**
     * To replace the list of users and update the recycler view with keeping position
     * @param newList The updated list of users
     */
    fun setListUser(newList: List<UserEntity>) {
        val diffCallback = UserDiffCallback(listUser, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        listUser.clear()
        listUser.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    // Provider reference custom type of view
    class ViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemRowUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Update avatar image
        Glide.with(holder.itemView).load(listUser[position].avatarUrl).into(holder.binding.ivAvatar)
        // Update username text
        holder.binding.tvUsername.text = listUser[position].username
        // Set click action
        holder.binding.itemRowUser.setOnClickListener {
            // Start user detail activity
            val intent = Intent(holder.itemView.context, UserDetailsActivity::class.java).apply {
                putExtra(UserDetailsActivity.EXTRA_USER, listUser[holder.adapterPosition].username)
            }
            holder.itemView.context.startActivity(intent)
        }
    }
}