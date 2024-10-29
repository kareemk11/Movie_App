package com.example.movieapp.di

import com.example.movieapp.all_movies.view_model.AllMoviesViewModel
import com.example.movieapp.model.IMovieRepository
import com.example.movieapp.model.MovieRepository
import com.example.movieapp.movie_details.view_model.MovieDetailsViewModel
import com.example.movieapp.network.IMovieRemoteDataSource
import com.example.movieapp.network.MovieRemoteDataSource
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<IMovieRemoteDataSource> { MovieRemoteDataSource.getInstance() }
    single<IMovieRepository> { MovieRepository.getInstance(get()) }
    viewModel { AllMoviesViewModel(get()) }
    viewModel { MovieDetailsViewModel(get()) }
}