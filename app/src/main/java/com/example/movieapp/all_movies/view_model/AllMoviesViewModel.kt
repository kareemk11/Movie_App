package com.example.movieapp.all_movies.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.AllMoviesState
import com.example.movieapp.model.IMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AllMoviesViewModel(private val repository: IMovieRepository) : ViewModel() {

    private val _popularMovies = MutableStateFlow<AllMoviesState>(AllMoviesState.Loading)
    val popularMovies: StateFlow<AllMoviesState> = _popularMovies.asStateFlow()

    private val _nowPlayingMovies = MutableStateFlow<AllMoviesState>(AllMoviesState.Loading)
    val nowPlayingMovies: StateFlow<AllMoviesState> = _nowPlayingMovies.asStateFlow()

    private val _upcomingMovies = MutableStateFlow<AllMoviesState>(AllMoviesState.Loading)
    val upcomingMovies: StateFlow<AllMoviesState> = _upcomingMovies.asStateFlow()

    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()


    fun updateSelectedTab(tab: Int) {
        _selectedTab.value = tab
    }


    fun updatePage(page: Int) {
        _currentPage.value = page
    }

    fun getPopularMovies(page: Int = 1) {
        _popularMovies.value = AllMoviesState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getPopularMovies(page).collect {
                    _popularMovies.value = AllMoviesState.Success(it.results)
                }
            } catch (e: Exception) {
                _popularMovies.value = AllMoviesState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getNowPlayingMovies(page: Int = 1) {
        _nowPlayingMovies.value = AllMoviesState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getNowPlayingMovies(page).collect {
                    _nowPlayingMovies.value = AllMoviesState.Success(it.results)
                }
            } catch (e: Exception) {
                _nowPlayingMovies.value = AllMoviesState.Error(e.message ?: "Unknown error")
            }
        }

    }

    fun getUpcomingMovies(page: Int = 1) {
        _upcomingMovies.value = AllMoviesState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getUpcomingMovies(page).collect {
                    _upcomingMovies.value = AllMoviesState.Success(it.results)
                }
            } catch (e: Exception) {
                _upcomingMovies.value = AllMoviesState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
