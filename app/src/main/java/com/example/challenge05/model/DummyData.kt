package com.example.challenge05.model

object DummyData {

    val dummyMovieResponseList = List(5) { pageIndex ->
        MovieResponse(
            dates = Dates(maximum = "2024-12-31", minimum = "2024-10-01"),
            page = pageIndex + 1,
            results = List(10) { movieIndex ->
                Movie(
                    adult = movieIndex % 2 == 0,
                    backdrop_path = "/dummy_backdrop_path${movieIndex + 1}.jpg",
                    genre_ids = listOf(28 + movieIndex, 12 + movieIndex),
                    id = (pageIndex * 10) + movieIndex + 1,
                    original_language = "en",
                    original_title = "Dummy Movie ${(pageIndex * 10) + movieIndex + 1}",
                    overview = "This is a dummy overview for movie ${(pageIndex * 10) + movieIndex + 1}.",
                    popularity = 7.0 + movieIndex,
                    poster_path = "/dummy_poster_path${movieIndex + 1}.jpg",
                    release_date = "2024-10-${10 + movieIndex}",
                    title = "Dummy Movie ${(pageIndex * 10) + movieIndex + 1}",
                    video = false,
                    vote_average = 8.0 + (movieIndex % 5),
                    vote_count = 1000 + (movieIndex * 100)
                )
            }
        )
    }


    val dummyMovieDetailsResponseList = List(10) { index ->
        MovieDetailsResponse(
            adult = index % 2 == 0,
            backdropPath = "/dummy_backdrop_path${index + 1}.jpg",
            belongsToCollection = if (index % 3 == 0) null else "Collection ${index / 3}",
            budget = 50000000 + (index * 1000000),
            genres = listOf(
                Genre(id = 28 + index, name = "Genre ${index + 1}"),
                Genre(id = 12 + index, name = "Genre ${index + 2}")
            ),
            homepage = "https://dummyhomepage${index + 1}.com",
            id = index + 1,
            imdbId = "tt1234${index + 1}",
            originCountry = listOf("US", "UK"),
            originalLanguage = "en",
            originalTitle = "Dummy Movie ${index + 1}",
            overview = "This is a detailed dummy overview for movie ${index + 1}.",
            popularity = 6.5 + index,
            posterPath = "/dummy_poster_path${index + 1}.jpg",
            productionCompanies = listOf(
                ProductionCompany(id = 101 + index, logoPath = "/dummy_logo${index + 1}.jpg", name = "Dummy Studio ${index + 1}", originCountry = "US")
            ),
            productionCountries = listOf(
                ProductionCountry(iso = "US", name = "United States"),
                ProductionCountry(iso = "UK", name = "United Kingdom")
            ),
            releaseDate = "2024-11-${10 + index}",
            revenue = 100000000 + (index * 5000000),
            runtime = 90 + index * 10,
            spokenLanguages = listOf(
                SpokenLanguage(englishName = "English", iso = "en", name = "English"),
                SpokenLanguage(englishName = "Spanish", iso = "es", name = "Español")
            ),
            status = "Released",
            tagline = "Tagline for dummy movie ${index + 1}.",
            title = "Dummy Movie ${index + 1}",
            video = index % 2 == 0,
            voteAverage = 7.5 + (index % 3),
            voteCount = 500 + (index * 100)
        )
    }

}