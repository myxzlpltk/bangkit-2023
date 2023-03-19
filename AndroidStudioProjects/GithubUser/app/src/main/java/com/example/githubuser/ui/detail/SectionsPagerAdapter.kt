package com.example.githubuser.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.data.local.entity.UserEntity
import com.example.githubuser.fragment.FollowListFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val user: UserEntity) :
    FragmentStateAdapter(activity) {
    fun isNeedToUpdate(user: UserEntity): Boolean {
        return user.followers != this.user.followers || user.following != this.user.following
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowListFragment().apply {
                arguments = Bundle().apply {
                    putString(FollowListFragment.ARG_TYPE, "followers")
                    putParcelable(FollowListFragment.ARG_USER, user)
                }
            }
            else -> FollowListFragment().apply {
                arguments = Bundle().apply {
                    putString(FollowListFragment.ARG_TYPE, "following")
                    putParcelable(FollowListFragment.ARG_USER, user)
                }
            }
        }
    }
}