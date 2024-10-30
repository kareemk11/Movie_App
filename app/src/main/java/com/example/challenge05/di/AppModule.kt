package com.example.challenge05.di

import com.example.challenge05.all_movies.view_model.AllMoviesViewModel
import com.example.challenge05.model.IMovieRepository
import com.example.challenge05.model.MovieRepository
import com.example.challenge05.movie_details.view_model.MovieDetailsViewModel
import com.example.challenge05.network.IMovieRemoteDataSource
import com.example.challenge05.network.MovieRemoteDataSource
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<IMovieRemoteDataSource> { MovieRemoteDataSource.getInstance() }
    single<IMovieRepository> { MovieRepository.getInstance(get()) }
    viewModel { AllMoviesViewModel(get()) }
    viewModel { MovieDetailsViewModel(get()) }
}