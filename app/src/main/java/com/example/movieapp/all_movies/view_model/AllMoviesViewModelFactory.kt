package com.example.movieapp.all_movies.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.model.IMovieRepository
import com.example.movieapp.model.MovieRepository

class AllMoviesViewModelFactory(private val repository: IMovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AllMoviesViewModel::class.java)) {
            AllMoviesViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")

        }
    }
}