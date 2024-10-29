package com.example.movieapp.all_movies.view_model

import com.example.movieapp.model.AllMoviesState
import com.example.movieapp.model.DummyData
import com.example.movieapp.model.FakeMovieRepository
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest


class AllMoviesViewModelTest {

    private lateinit var viewModel: AllMoviesViewModel
    private lateinit var repository: FakeMovieRepository

    @Before
    fun setUp() {
        repository = FakeMovieRepository(
            DummyData.dummyMovieResponseList,
            DummyData.dummyMovieDetailsResponseList
        )
        viewModel = AllMoviesViewModel(repository)
    }

    // update methods
    @Test
    fun updatePage_withValidPage_updatesCurrentPage() {
        val page = 2
        viewModel.updatePage(page)
        assertEquals(page, viewModel.currentPage.value)
    }

    @Test
    fun updateSelectedTab_withValidTab_updatesSelectedTab() {
        val tab = 1
        viewModel.updateSelectedTab(tab)
        assertEquals(tab, viewModel.selectedTab.value)
    }

    // tests for getPopularMovies
    @Test
    fun getPopularMovies_withValidPage_returnsCorrectMovieList() = runTest {
        val page = 1
        viewModel.getPopularMovies(page)
        advanceUntilIdle()
        val result = viewModel.popularMovies.value as AllMoviesState.Success
        assertEquals(DummyData.dummyMovieResponseList[page - 1].results, result.movies)
    }

    @Test
    fun getPopularMovies_showsLoadingState() = runTest {
        viewModel.getPopularMovies()
        assertTrue(viewModel.popularMovies.value is AllMoviesState.Loading)
    }

    @Test
    fun getPopularMovies_whenExceptionOccurs_updatesStateToError() = runTest {
        val faultyRepository = FakeMovieRepository(emptyList(), DummyData.dummyMovieDetailsResponseList) // Simulating an empty response
        val faultyViewModel = AllMoviesViewModel(faultyRepository)

        faultyViewModel.getPopularMovies(1)
        advanceUntilIdle()

        assertTrue(faultyViewModel.popularMovies.value is AllMoviesState.Error)
    }

    // tests for getNowPlayingMovies
    @Test
    fun getNowPlayingMovies_withValidPage_returnsCorrectMovieList() = runTest {
        val page = 1
        viewModel.getNowPlayingMovies(page)
        advanceUntilIdle()
        val result = viewModel.nowPlayingMovies.value as AllMoviesState.Success
        assertEquals(DummyData.dummyMovieResponseList[page - 1].results, result.movies)
    }

    @Test
    fun getNowPlayingMovies_showsLoadingState() = runTest {
        viewModel.getNowPlayingMovies()
        assertTrue(viewModel.nowPlayingMovies.value is AllMoviesState.Loading)
    }

    @Test
    fun getNowPlayingMovies_whenExceptionOccurs_updatesStateToError() = runTest {
        val faultyRepository = FakeMovieRepository(emptyList(), DummyData.dummyMovieDetailsResponseList)
        val faultyViewModel = AllMoviesViewModel(faultyRepository)

        faultyViewModel.getNowPlayingMovies(1)
        advanceUntilIdle()

        assertTrue(faultyViewModel.nowPlayingMovies.value is AllMoviesState.Error)
    }

    // tests for getUpcomingMovies
    @Test
    fun getUpcomingMovies_withValidPage_returnsCorrectMovieList() = runTest {
        val page = 1
        viewModel.getUpcomingMovies(page)
        advanceUntilIdle()
        val result = viewModel.upcomingMovies.value as AllMoviesState.Success
        assertEquals(DummyData.dummyMovieResponseList[page - 1].results, result.movies)
    }

    @Test
    fun getUpcomingMovies_showsLoadingState() = runTest {
        viewModel.getUpcomingMovies()
        assertTrue(viewModel.upcomingMovies.value is AllMoviesState.Loading)
    }

    @Test
    fun getUpcomingMovies_whenExceptionOccurs_updatesStateToError() = runTest {
        val faultyRepository = FakeMovieRepository(emptyList(), DummyData.dummyMovieDetailsResponseList)
        val faultyViewModel = AllMoviesViewModel(faultyRepository)

        faultyViewModel.getUpcomingMovies(1)
        advanceUntilIdle()

        assertTrue(faultyViewModel.upcomingMovies.value is AllMoviesState.Error)
    }
}