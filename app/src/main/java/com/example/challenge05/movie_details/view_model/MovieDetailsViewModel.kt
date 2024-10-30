package com.example.challenge05.movie_details.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge05.model.IMovieRepository
import com.example.challenge05.model.MovieDetailsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel (private val repository: IMovieRepository): ViewModel() {


    private val _movieDetails = MutableStateFlow<MovieDetailsState>(MovieDetailsState.Loading)
    val movieDetails: StateFlow<MovieDetailsState> = _movieDetails.asStateFlow()

     fun getMovieDetails(movieId: Int) {

         viewModelScope.launch(Dispatchers.IO) {
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


}