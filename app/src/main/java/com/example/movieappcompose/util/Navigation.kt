package com.example.movieappcompose.util

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieappcompose.presentation.favourites.FavouritesScreen
import com.example.movieappcompose.presentation.home.HomeScreen
import com.example.movieappcompose.presentation.on_boarding.OnBoardingScreen
import com.example.movieappcompose.presentation.person.PersonScreen
import com.example.movieappcompose.presentation.video.VideoScreen


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
            HomeScreen()
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
    }
}