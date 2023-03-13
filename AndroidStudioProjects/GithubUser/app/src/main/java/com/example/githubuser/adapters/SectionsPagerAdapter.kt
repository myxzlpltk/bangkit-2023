package com.example.githubuser.adapters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubuser.fragments.FollowListFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowListFragment().apply {
                arguments = Bundle().apply {
                    putString(FollowListFragment.ARG_TYPE, "followers")
                }
            }
            else -> FollowListFragment().apply {
                arguments = Bundle().apply {
                    putString(FollowListFragment.ARG_TYPE, "followings")
                }
            }
        }
    }
}