package com.example.challengechapterlima.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.challengechapterlima.R
import com.example.challengechapterlima.databinding.FragmentDetailBinding
import com.example.challengechapterlima.local.entity.FavoriteEntity
import com.example.challengechapterlima.model.DetailMovieResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private var isFavorite by Delegates.notNull<Boolean>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetailMovie(arguments?.getInt("MOVIE_ID")!!)
        viewModel.session()
        bind()
        viewModel.isFavorite.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.favoriteFab.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                binding.favoriteFab.setImageResource(R.drawable.baseline_favorite_border_24)
            }
            isFavorite = it
        }
    }

    private fun selectFavorite(detailMovieResponse: DetailMovieResponse) {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                viewModel.isFavorite(detailMovieResponse.id, user.uid)
                binding.favoriteFab.setOnClickListener {
                    if (!isFavorite) {
                        viewModel.addFavorite(
                            FavoriteEntity(
                                id_movie = detailMovieResponse.id,
                                photo = detailMovieResponse.posterPath,
                                title = detailMovieResponse.title,
                                uuid_user = user.uid
                            )
                        )
                        binding.favoriteFab.setImageResource(R.drawable.baseline_favorite_24)
                        isFavorite = true
                    } else {
                        viewModel.deleteFavorite(detailMovieResponse.id, user.uid)
                        binding.favoriteFab.setImageResource(R.drawable.baseline_favorite_border_24)
                        isFavorite = false
                    }
                }
            }
        }

    }

    private fun bind() {
        viewModel.loadingState.observe(viewLifecycleOwner) {
            binding.pb.isVisible = it
            binding.name.isVisible = !it
            binding.photo.isVisible = !it
            binding.backdrop.isVisible = !it
            binding.desc.isVisible = !it
        }
        viewModel.movie.observe(viewLifecycleOwner) {
            val imageUrl = "https://image.tmdb.org/t/p/w500"
            Glide.with(requireContext()).load(imageUrl + it.backdropPath).into(binding.backdrop)
            Glide.with(requireContext()).load(imageUrl + it.posterPath).into(binding.photo)
            binding.name.text = it.title
            binding.topAppBar.title = it.title
            binding.desc.text = it.overview
            selectFavorite(it)
        }
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}