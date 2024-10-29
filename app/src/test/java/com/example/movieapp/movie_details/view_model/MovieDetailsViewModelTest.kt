package com.example.movieapp.movie_details.view_model

import com.example.movieapp.model.DummyData
import com.example.movieapp.model.FakeMovieRepository
import com.example.movieapp.model.MovieDetailsState
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class MovieDetailsViewModelTest {

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var repository: FakeMovieRepository

    @Before
    fun setUp() {

        repository = FakeMovieRepository(
            DummyData.dummyMovieResponseList,
            DummyData.dummyMovieDetailsResponseList
        )
        viewModel = MovieDetailsViewModel(repository)
    }


    @Test
    fun getMovieDetails_showsLoadingState() = runTest {
        viewModel.getMovieDetails(1)
        assertTrue(viewModel.movieDetails.value is MovieDetailsState.Loading)
    }

    @Test
    fun getMovieDetails_withValidId_returnsMovieDetails() = runTest {
        val movieId = 0
        viewModel.getMovieDetails(movieId)
        advanceUntilIdle()

        val result = viewModel.movieDetails.value as MovieDetailsState.Success
        assertEquals(DummyData.dummyMovieDetailsResponseList[movieId], result.movie)
    }

    @Test
    fun getMovieDetails_withNonExistingId_updatesStateToError() = runTest {
        val nonExistingMovieId = 999
        viewModel.getMovieDetails(nonExistingMovieId)
        advanceUntilIdle()

        assertTrue(viewModel.movieDetails.value is MovieDetailsState.Error)
    }

    @Test
    fun getMovieDetails_whenExceptionOccurs_updatesStateToError() = runTest {
        val faultyRepository = FakeMovieRepository(emptyList(), emptyList())
        val faultyViewModel = MovieDetailsViewModel(faultyRepository)

        faultyViewModel.getMovieDetails(1)
        advanceUntilIdle()

        assertTrue(faultyViewModel.movieDetails.value is MovieDetailsState.Error)
    }

    @Test
    fun getMovieDetails_withEmptyList_returnsError() = runTest {
        val emptyRepository = FakeMovieRepository(emptyList(), emptyList())
        val emptyViewModel = MovieDetailsViewModel(emptyRepository)

        emptyViewModel.getMovieDetails(0)
        advanceUntilIdle()

        assertTrue(emptyViewModel.movieDetails.value is MovieDetailsState.Error)
    }
}
