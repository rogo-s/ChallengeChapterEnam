package com.example.challengechapterlima.register

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.challengechapterlima.R
import com.example.challengechapterlima.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmailRegister.text.toString()
            val password = binding.etPasswordRegister.text.toString()

            if (email.isEmpty()) {
                binding.etEmailRegister.error = "Email Harus Diisi"
                binding.etEmailRegister.requestFocus()
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etPasswordRegister.error = "Email Tidak Valid"
                binding.etPasswordRegister.requestFocus()
            }
            if (password.isEmpty()) {
                binding.etPasswordRegister.error = "Password Harus Di Isi"
                binding.etPasswordRegister.requestFocus()
            }
            if (!binding.tilEmail.isErrorEnabled && !binding.inputTILPassword.isErrorEnabled) {
                viewModel.registerFirebase(email, password)
            }
        }
        viewModel.register.observe(viewLifecycleOwner) {
            if (it.equals("Register Success!", true)) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment2)
            } else {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }
}


