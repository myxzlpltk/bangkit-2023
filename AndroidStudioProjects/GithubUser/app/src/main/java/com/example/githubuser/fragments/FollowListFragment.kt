package com.example.githubuser.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.adapters.ListUserAdapter
import com.example.githubuser.databinding.FragmentFollowListBinding
import com.example.githubuser.networks.UserResponse
import com.example.githubuser.view_models.ListFollowViewModel

class FollowListFragment : Fragment() {

    companion object {
        const val ARG_TYPE = "type"
        const val ARG_USERNAME = "username"
        const val ARG_TOTAL = "total"
    }

    private lateinit var binding: FragmentFollowListBinding
    private val viewModel: ListFollowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFollowListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val type = (arguments as Bundle).getString(ARG_TYPE, "")
        val username = (arguments as Bundle).getString(ARG_USERNAME, "")
        val total = (arguments as Bundle).getInt(ARG_TOTAL, 0)
        viewModel.findFollow(type, username, total)

        /* Setup user */
        binding.rvListFollow.setHasFixedSize(true)
        binding.rvListFollow.layoutManager = LinearLayoutManager(activity)
        binding.rvListFollow.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadFollow(type, username, total)
                }
            }
        })

        /* Observe update data */
        viewModel.isLoading.observe(viewLifecycleOwner) { showLoading(it) }
        viewModel.users.observe(viewLifecycleOwner) { setUsersData(it) }
        viewModel.toastText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { text ->
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUsersData(users: List<UserResponse>) {
        if (viewModel.isFirstPage) {
            /* Reset position */
            binding.rvListFollow.adapter = ListUserAdapter(users)
        } else {
            /* Maintain position */
            val recyclerViewState = binding.rvListFollow.layoutManager?.onSaveInstanceState()
            binding.rvListFollow.adapter = ListUserAdapter(users)
            binding.rvListFollow.layoutManager?.onRestoreInstanceState(recyclerViewState)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}