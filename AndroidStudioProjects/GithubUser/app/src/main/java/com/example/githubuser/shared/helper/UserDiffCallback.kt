package com.example.githubuser.shared.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuser.data.remote.response.SimpleUser

class UserDiffCallback(
    private val oldList: List<SimpleUser>,
    private val newList: List<SimpleUser>,
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
        return oldList[oldPos].login == newList[newPos].login
    }

    override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
        val old = oldList[oldPos]
        val new = newList[newPos]

        return old.login == new.login && old.id == new.id && old.avatarUrl == new.avatarUrl
    }
}