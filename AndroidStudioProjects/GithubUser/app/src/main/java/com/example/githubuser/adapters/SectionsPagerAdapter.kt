package com.example.githubuser.adapters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.fragments.FollowListFragment
import com.example.githubuser.networks.UserDetailsResponse

class SectionsPagerAdapter(activity: AppCompatActivity, private val user: UserDetailsResponse) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowListFragment().apply {
                arguments = Bundle().apply {
                    putString(FollowListFragment.ARG_TYPE, "followers")
                    putString(FollowListFragment.ARG_USERNAME, user.login.lowercase())
                    putInt(FollowListFragment.ARG_TOTAL, user.followers)
                }
            }
            else -> FollowListFragment().apply {
                arguments = Bundle().apply {
                    putString(FollowListFragment.ARG_TYPE, "following")
                    putString(FollowListFragment.ARG_USERNAME, user.login.lowercase())
                    putInt(FollowListFragment.ARG_TOTAL, user.following)
                }
            }
        }
    }
}