package com.example.movieappcompose.util


sealed class Screen(val route: String){
    object OnBoardingScreen : Screen("onBoarding_Screen")
    object HomeScreen : Screen("home_screen")
    object VideoScreen : Screen("video_screen")
    object FavouritesScreen : Screen("favourites_screen")
    object PersonScreen : Screen("person_screen")
    object ViewAllPopularMoviesScreen: Screen("view_all_popular_movies_screen")
    object SearchScreen : Screen("search_screen")
    object MovieDetailScreen : Screen("movie_detail_screen")
}