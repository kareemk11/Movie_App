package com.example.movieapp.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.model.MovieRepository

class MovieDetailsViewModelFactory (private val repository: MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            MovieDetailsViewModel (repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}