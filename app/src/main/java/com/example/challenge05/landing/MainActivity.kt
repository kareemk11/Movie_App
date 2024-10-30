package com.example.challenge05.landing


import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.challenge05.movie_details.view.MovieDetailsScreen
import com.example.challenge05.movie_details.view_model.MovieDetailsViewModel
import com.example.challenge05.all_movies.view.AllMoviesScreen
import com.example.challenge05.all_movies.view_model.AllMoviesViewModel
import com.example.challenge05.di.appModule
import com.example.challenge05.no_internet.ConnectivityObserver
import com.example.challenge05.no_internet.NoInternetScreen
import com.example.challenge05.splash.SplashScreen
import com.example.challenge05.ui.theme.MovieAppTheme
import com.example.challenge05.utils.Screens
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : ComponentActivity() {

//    private val repository by lazy { MovieRepository.getInstance(MovieRemoteDataSource.getInstance()) }
//    private val allMoviesViewModel by lazy { AllMoviesViewModelFactory(repository).create(AllMoviesViewModel::class.java) }
//    private val movieDetailsViewModel by lazy { MovieDetailsViewModelFactory(repository).create(MovieDetailsViewModel::class.java) }
    private lateinit var connectivityObserver: ConnectivityObserver
    private var isShowingNoInternetScreen = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }
        val allMoviesViewModel: AllMoviesViewModel = getViewModel()
        val movieDetailsViewModel: MovieDetailsViewModel = getViewModel()
        super.onCreate(savedInstanceState)
        connectivityObserver = ConnectivityObserver(applicationContext)

        setContent {
            MovieAppTheme {
                val navController = rememberNavController()
                val connectivityStatus = connectivityObserver.isConnected.collectAsState(initial = true)

                LaunchedEffect(connectivityStatus.value) {
                    if (connectivityStatus.value) {
                        if (isShowingNoInternetScreen) {
                            navController.popBackStack()
                            isShowingNoInternetScreen = false
                        }
                    } else {
                        navController.navigate(Screens.NoInternet.route)
                        isShowingNoInternetScreen = true
                    }
                }

                NavHost(navController = navController, startDestination = Screens.Splash.route) {
                    composable("no_internet") {
                        NoInternetScreen(onRetry = {
                            if (connectivityStatus.value) {
                                navController.popBackStack()
                                isShowingNoInternetScreen = false
                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "No internet connection available",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                    }
                    composable(Screens.Splash.route) {
                        SplashScreen {
                            navController.navigate(Screens.AllMovies.route) {
                                popUpTo(0) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                    composable(Screens.AllMovies.route) {
                        AllMoviesScreen(viewModel = allMoviesViewModel, navController = navController)
                    }
                    composable(Screens.MovieDetails.route) {
                        MovieDetailsScreen(viewModel = movieDetailsViewModel, navController = navController)
                    }
                }
            }
        }
    }
}







