package com.example.githubuser.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.adapters.ListUserAdapter
import com.example.githubuser.databinding.FragmentFollowListBinding
import com.example.githubuser.networks.UserDetailsResponse
import com.example.githubuser.networks.UserResponse
import com.example.githubuser.view_models.ListFollowViewModel
import com.example.githubuser.view_models.ListFollowViewModelFactory

class FollowListFragment : Fragment() {

    companion object {
        const val ARG_TYPE = "type"
        const val ARG_USER = "user"
    }

    private var _binding: FragmentFollowListBinding? = null
    private val binding get() = _binding!!

    private var _viewModel: ListFollowViewModel? = null
    private val viewModel get() = _viewModel!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFollowListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Get Bundle */
        val mBundle = arguments as Bundle
        val type = mBundle.getString(ARG_TYPE, "")
        val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mBundle.getParcelable(ARG_USER, UserDetailsResponse::class.java)
        } else {
            @Suppress("DEPRECATION") mBundle.getParcelable(ARG_USER)
        } as UserDetailsResponse

        /* Get user */
        val username = user.login.lowercase()
        val total = if (type == "following") user.following else user.followers

        /* Setup viewModel */
        _viewModel = ViewModelProvider(
            this, ListFollowViewModelFactory(type, username, total)
        )[ListFollowViewModel::class.java]

        /* Setup user */
        binding.rvListFollow.setHasFixedSize(true)
        binding.rvListFollow.layoutManager = LinearLayoutManager(activity)
        binding.rvListFollow.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadFollow()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _viewModel = null
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