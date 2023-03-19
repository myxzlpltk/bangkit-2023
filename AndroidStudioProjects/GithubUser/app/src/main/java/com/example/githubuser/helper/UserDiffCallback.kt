package com.example.githubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuser.data.local.entity.UserEntity

class UserDiffCallback(
    private val oldList: List<UserEntity>,
    private val newList: List<UserEntity>,
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