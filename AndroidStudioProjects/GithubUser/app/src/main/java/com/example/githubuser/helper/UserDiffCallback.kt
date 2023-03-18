package com.example.githubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuser.data.remote.response.UserResponse

class UserDiffCallback(
    private val oldList: List<UserResponse>,
    private val newList: List<UserResponse>,
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
        return oldList[oldPos].username == newList[newPos].username
    }

    override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
        val old = oldList[oldPos]
        val new = newList[newPos]

        return old.username == new.username && old.id == new.id && old.avatarUrl == new.avatarUrl
    }
}