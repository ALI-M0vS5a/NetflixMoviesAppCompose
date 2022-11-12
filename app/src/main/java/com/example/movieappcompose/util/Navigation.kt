package com.example.movieappcompose.util

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.movieappcompose.presentation.favourites.FavouritesScreen
import com.example.movieappcompose.presentation.home.HomeScreen
import com.example.movieappcompose.presentation.movie_detail.MovieDetailScreen
import com.example.movieappcompose.presentation.on_boarding.OnBoardingScreen
import com.example.movieappcompose.presentation.person.PersonScreen
import com.example.movieappcompose.presentation.search.SearchScreen
import com.example.movieappcompose.presentation.video.VideoScreen
import com.example.movieappcompose.presentation.view_all.ViewAllPopularMoviesScreen
import com.google.accompanist.pager.ExperimentalPagerApi


@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun Navigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.OnBoardingScreen.route
    ) {
        composable(route = Screen.OnBoardingScreen.route) {
            OnBoardingScreen(
                navController = navController
            )
        }
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                navController = navController
            )
        }
        composable(route = Screen.VideoScreen.route) {
            VideoScreen()
        }
        composable(route = Screen.FavouritesScreen.route) {
            FavouritesScreen()
        }
        composable(route = Screen.PersonScreen.route) {
            PersonScreen()
        }
        composable(
            route = Screen.ViewAllPopularMoviesScreen.route
        ) {
            ViewAllPopularMoviesScreen(
                navController = navController
            )
        }
        composable(route = Screen.SearchScreen.route) {
            SearchScreen(
                navController = navController
            )
        }
        composable(
            route = Screen.MovieDetailScreen.route + "?movie_id={movie_id}",
            arguments = listOf(
                navArgument(name = "movie_id") {
                    type = NavType.IntType
                }
            )
        ) {
            MovieDetailScreen()
        }
    }
}