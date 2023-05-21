package com.example.challengechapterlima.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.challengechapterlima.R
import com.example.challengechapterlima.databinding.FragmentProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.session()
        val title = activity as AppCompatActivity
        viewModel.user.observe(viewLifecycleOwner) {
            title.supportActionBar?.title = "Hi, ${it?.email}"
            binding.apply {
                etEmail.setText(it?.email.toString())
            }
        }
        binding.apply {
            btnUpdate.setOnClickListener {
                val email = etEmail.text.toString().trim()
                viewModel.updateEmail(email)
                viewModel.update.observe(viewLifecycleOwner) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.profileFragment)
                }
            }
            btnLogout.setOnClickListener {
                Firebase.auth.signOut()
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}