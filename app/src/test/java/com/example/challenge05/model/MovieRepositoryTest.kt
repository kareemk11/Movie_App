package com.example.challenge05.model

import com.example.challenge05.network.FakeRemoteDataSource
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class MovieRepositoryTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var remoteDataSource: FakeRemoteDataSource

    @Before
    fun setUp() {

        remoteDataSource = FakeRemoteDataSource(
            DummyData.dummyMovieResponseList,
            DummyData.dummyMovieDetailsResponseList
        )
        movieRepository = MovieRepository.getInstance(remoteDataSource)

    }

    // Tests for getPopularMovies
    @Test
    fun getPopularMovies_withValidPage_returnsCorrectMovieList() = runTest {
        val page = 1
        val result = movieRepository.getPopularMovies(page).first()
        assertEquals(DummyData.dummyMovieResponseList[page - 1], result)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun getPopularMovies_withInvalidPage_throwsException() = runTest {
        val invalidPage = DummyData.dummyMovieResponseList.size + 1
        movieRepository.getPopularMovies(invalidPage).first()
    }

    @Test
    fun getPopularMovies_withFirstPage_returnsNonEmptyList() = runTest {
        val result = movieRepository.getPopularMovies(1).first()
        assertTrue(result.results.isNotEmpty())
    }

    @Test
    fun getPopularMovies_withBoundaryPage_returnsNonEmptyList() = runTest {
        val lastPage = DummyData.dummyMovieResponseList.size - 1
        val result = movieRepository.getPopularMovies(lastPage).first()
        assertTrue(result.results.isNotEmpty())
    }

    @Test
    fun getPopularMovies_withNonExistingPage_returnsEmptyList() = runTest {
        val nonExistingPage = DummyData.dummyMovieResponseList.size + 1
        val result = movieRepository.getPopularMovies(nonExistingPage).toList()
        assertTrue(result.isEmpty())
    }



    // Tests for getNowPlayingMovies
    @Test
    fun getNowPlayingMovies_withValidPage_returnsCorrectMovieList() = runTest {
        val page = 1
        val result = movieRepository.getNowPlayingMovies(page).first()
        assertEquals(DummyData.dummyMovieResponseList[page], result)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun getNowPlayingMovies_withInvalidPage_throwsException() = runTest {
        val invalidPage = DummyData.dummyMovieResponseList.size + 1
        movieRepository.getNowPlayingMovies(invalidPage).first()
    }

    @Test
    fun getNowPlayingMovies_withBoundaryPage_returnsNonEmptyList() = runTest {
        val lastPage = DummyData.dummyMovieResponseList.size - 1
        val result = movieRepository.getNowPlayingMovies(lastPage).first()
        assertTrue(result.results.isNotEmpty())
    }

    @Test fun getNowPlayingMovies_withNonExistingPage_returnsEmptyList() = runTest {
        val nonExistingPage = DummyData.dummyMovieResponseList.size + 1
        val result = movieRepository.getNowPlayingMovies(nonExistingPage).toList()
        assertTrue(result.isEmpty())
    }

    // Tests for getUpcomingMovies
    @Test
    fun getUpcomingMovies_withValidPage_returnsCorrectMovieList() = runTest {
        val page = 2
        val result = movieRepository.getUpcomingMovies(page).first()
        assertEquals(DummyData.dummyMovieResponseList[page], result)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun getUpcomingMovies_withInvalidPage_throwsException() = runTest {
        val invalidPage = DummyData.dummyMovieResponseList.size + 2
        movieRepository.getUpcomingMovies(invalidPage).first()
    }

    @Test
    fun getUpcomingMovies_withNonExistingPage_returnsEmptyList() = runTest {
        val nonExistingPage = DummyData.dummyMovieResponseList.size + 1
        val result = movieRepository.getUpcomingMovies(nonExistingPage).toList()
        assertTrue(result.isEmpty())
    }

    // Tests for getMovieDetails
    @Test
    fun getMovieDetails_withValidMovieId_returnsCorrectMovieDetails() = runTest {
        val movieId = 0
        val result = movieRepository.getMovieDetails(movieId).first()
        assertEquals(DummyData.dummyMovieDetailsResponseList[movieId], result)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun getMovieDetails_withInvalidMovieId_throwsException() = runTest {
        val invalidMovieId = DummyData.dummyMovieDetailsResponseList.size + 1
        movieRepository.getMovieDetails(invalidMovieId).first()
    }

    @Test
    fun getMovieDetails_withBoundaryMovieId_returnsNonEmptyDetails() = runTest {
        val lastMovieId = DummyData.dummyMovieDetailsResponseList.size - 1
        val result = movieRepository.getMovieDetails(lastMovieId).first()
        assertTrue(result.genres.isNotEmpty())
    }

    @Test
    fun getMovieDetails_withNonExistingMovieId_returnsEmptyDetails() = runTest {
        val nonExistingMovieId = DummyData.dummyMovieDetailsResponseList.size + 1
        val result = movieRepository.getMovieDetails(nonExistingMovieId).toList()
        assertTrue(result.isEmpty())
    }



}