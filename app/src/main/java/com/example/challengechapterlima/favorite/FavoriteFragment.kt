package com.example.challengechapterlima.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.challengechapterlima.R
import com.example.challengechapterlima.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var mainAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        viewModel.session()
        viewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                viewModel.getAllFavorites(it.uid)
            }
        }
        viewModel.favorite.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.tvNoData.isVisible = false
                binding.rvFavMovie.isVisible = true
                mainAdapter.differ.submitList(it)
            } else {
                binding.tvNoData.isVisible = true
                binding.rvFavMovie.isVisible = false
            }
        }
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_favoriteFragment_to_homeFragment)
        }
    }

    private fun initList() {
        mainAdapter = FavoriteAdapter()
        binding.rvFavMovie.apply {
            adapter = mainAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}