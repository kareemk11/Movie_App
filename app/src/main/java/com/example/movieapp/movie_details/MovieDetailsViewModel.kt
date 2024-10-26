package com.example.movieapp.movie_details

import androidx.lifecycle.ViewModel
import com.example.movieapp.model.MovieDetailsState
import com.example.movieapp.model.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MovieDetailsViewModel (private val repository: MovieRepository): ViewModel() {


    private val _movieDetails = MutableStateFlow<MovieDetailsState>(MovieDetailsState.Loading)
    val movieDetails: StateFlow<MovieDetailsState> = _movieDetails

    suspend fun getMovieDetails(movieId: Int) {
        _movieDetails.value = MovieDetailsState.Loading
        try {
             repository.getMovieDetails(movieId).collect { movieDetails ->
                 _movieDetails.value = MovieDetailsState.Success(movieDetails)
             }
            } catch (e: Exception) {
            _movieDetails.value = MovieDetailsState.Error(e.message ?: "Unknown error")
        }
    }


}