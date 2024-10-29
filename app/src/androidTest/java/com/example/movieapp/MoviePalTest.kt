package com.example.movieapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.all_movies.view.AllMoviesScreen
import com.example.movieapp.all_movies.view_model.AllMoviesViewModel
import com.example.movieapp.model.DummyData
import com.example.movieapp.model.IMovieRepository
import com.example.movieapp.movie_details.components.MovieDetailsContent
import com.example.movieapp.movie_details.view_model.MovieDetailsViewModel
import com.example.movieapp.splash.SplashScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoviePalTest {


    private lateinit var allMoviesViewModel: AllMoviesViewModel
    private lateinit var detailsViewModel: MovieDetailsViewModel
    private lateinit var repository: IMovieRepository


    @Before
    fun setup() {
        repository = FakeRepo()
        allMoviesViewModel = AllMoviesViewModel(
            repository = repository
        )
        detailsViewModel = MovieDetailsViewModel(
            repository = repository
        )

    }

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testSplashScreenExitsAfterDelay() {
        rule.setContent {
            SplashScreen(
                onSplashComplete = {}
            )
        }

        rule.waitUntil(timeoutMillis = 1000) {
            rule.onAllNodesWithText("MoviePal").fetchSemanticsNodes().isNotEmpty()
        }

        rule.onNodeWithText("MoviePal").assertIsDisplayed()
        rule.onNodeWithText("Your Personal Movie Companion").assertIsDisplayed()
    }


    @Test
    fun testAllMoviesScreen() {
        rule.setContent {
            val navController = rememberNavController()
            AllMoviesScreen(navController = navController, viewModel = allMoviesViewModel)
        }
        rule.onNodeWithText("Movie Pal").assertIsDisplayed()
        rule.onNodeWithText("Upcoming").assertIsDisplayed()
        rule.onNodeWithText("Popular").assertIsDisplayed()
        rule.onNodeWithText("Now Playing").assertIsDisplayed()
        rule.onNodeWithText("Upcoming").performClick()
        rule.onNodeWithText("Popular").performClick()
        rule.onNodeWithText("Now Playing").performClick()
        rule.onNodeWithText("1").assertIsDisplayed().performClick()
        rule.onNodeWithText("2").assertIsDisplayed().performClick()
        rule.onNodeWithText("3").assertIsDisplayed().performClick()
        rule.onNodeWithText("4").assertIsDisplayed().performClick()
        rule.onNodeWithText("5").assertIsDisplayed().performClick()
    }


    @Test
    fun testMovieDetailsScreen() {
        rule.setContent {
            val navController = rememberNavController()
            MovieDetailsContent(
                details = DummyData.dummyMovieDetailsResponseList[0],
                onBackPressed = { navController.popBackStack() }
            )
        }


        rule.onNodeWithText("Overview").assertIsDisplayed()
        rule.onNodeWithText("Genres").assertIsDisplayed()
        rule.onNodeWithText("Votes").assertIsDisplayed()
        rule.onNodeWithText("Popularity").assertIsDisplayed()
        rule.onNodeWithText("Budget").assertIsDisplayed()
        rule.onNodeWithText("Production Companies").assertIsDisplayed()
        rule.onNodeWithContentDescription("Go Back").assertIsDisplayed().performClick()
            .assertExists()
    }


}