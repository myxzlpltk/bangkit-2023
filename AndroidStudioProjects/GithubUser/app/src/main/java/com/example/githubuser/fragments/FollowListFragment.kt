package com.example.githubuser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.FragmentFollowListBinding
import com.example.githubuser.view_models.UserDetailsViewModel

class FollowListFragment : Fragment() {

    companion object {
        const val ARG_TYPE = "type"
    }

    private lateinit var binding: FragmentFollowListBinding
    private val viewModel: UserDetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFollowListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Setup user */
        binding.textView.text = arguments?.getString(ARG_TYPE)
        binding.rvListFollow.setHasFixedSize(true)
        binding.rvListFollow.layoutManager = LinearLayoutManager(activity)

        /* Observe update data */
    }
}