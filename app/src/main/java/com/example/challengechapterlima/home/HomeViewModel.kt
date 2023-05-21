package com.example.challengechapterlima.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengechapterlima.model.MovieResponse
import com.example.challengechapterlima.repository.NetworkRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val networkRepository: NetworkRepository): ViewModel() {
    private val _movie = MutableLiveData<MovieResponse>()
    val movie: LiveData<MovieResponse> = _movie

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> = _user


    fun getAllMoviesNowPlaying() = viewModelScope.launch(Dispatchers.IO) {
        _loadingState.postValue(true)
        val response = networkRepository.getAllMoviesNowPlaying()
        viewModelScope.launch(Dispatchers.Main) {
            _loadingState.postValue(false)
            _movie.postValue(response)
        }
    }

    fun session() {
        if (Firebase.auth.currentUser != null) {
            _user.postValue(Firebase.auth.currentUser)
        } else {
            _user.postValue(null)
        }
    }
}