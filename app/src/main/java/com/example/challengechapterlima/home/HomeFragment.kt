package com.example.challengechapterlima.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.challengechapterlima.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var mainAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.session()
        initList()
        viewModel.getAllMoviesNowPlaying()
        viewModel.loadingState.observe(viewLifecycleOwner) {
            binding.pb.isVisible = it
            binding.rvMovie.isVisible = !it
        }
        viewModel.movie.observe(viewLifecycleOwner) {
            mainAdapter.differ.submitList(it.results)
        }
        val title = activity as AppCompatActivity
        viewModel.user.observe(viewLifecycleOwner) {
            title.supportActionBar?.title = "Hi, ${it?.email}"
        }

    }

    private fun initList() {
        mainAdapter = HomeAdapter()
        binding.rvMovie.apply {
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