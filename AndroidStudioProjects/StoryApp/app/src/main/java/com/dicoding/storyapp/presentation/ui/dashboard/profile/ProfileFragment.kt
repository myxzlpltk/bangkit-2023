package com.dicoding.storyapp.presentation.ui.dashboard.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dicoding.storyapp.databinding.FragmentProfileBinding
import com.dicoding.storyapp.presentation.ui.sign_in.SignInActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupActions()
    }

    private fun setupView() {
        viewModel.userLiveData.observe(viewLifecycleOwner) { user ->
            binding.tvName.text = user.name
        }
    }

    private fun setupActions() {
        binding.signOutButton.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(activity, SignInActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}