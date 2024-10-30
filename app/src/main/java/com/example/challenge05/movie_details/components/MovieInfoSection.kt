package com.example.challenge05.movie_details.components


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.challenge05.model.MovieDetailsResponse

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieInfoSection(details: MovieDetailsResponse) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        TitleAndRatingSection(details)
        QuickInfoSection(details)
        Spacer(modifier = Modifier.height(16.dp))
        StatsSection(details)
        Spacer(modifier = Modifier.height(16.dp))
        GenresSection(details.genres)
        Spacer(modifier = Modifier.height(16.dp))

        OverviewSection(details.overview)
        Spacer(modifier = Modifier.height(16.dp))

        ProductionCompaniesSection(details.productionCompanies)
        Spacer(modifier = Modifier.height(16.dp))

        AdultContentWarning(details.adult)
    }
}
