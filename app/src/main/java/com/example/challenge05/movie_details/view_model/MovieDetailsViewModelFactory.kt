package com.example.challenge05.movie_details.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.challenge05.model.IMovieRepository

class MovieDetailsViewModelFactory (private val repository: IMovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            MovieDetailsViewModel (repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}