package com.example.movieappcompose.util


sealed class Screen(val route: String){
    object OnBoardingScreen : Screen("onBoarding_Screen")
    object HomeScreen : Screen("home_screen")
    object VideoScreen : Screen("video_screen")
    object FavouritesScreen : Screen("favourites_screen")
    object PersonScreen : Screen("person_screen")
}