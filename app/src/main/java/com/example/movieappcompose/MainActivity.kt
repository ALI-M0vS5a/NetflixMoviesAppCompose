package com.example.movieappcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movieappcompose.presentation.components.ExpandableBottomNavigationBar
import com.example.movieappcompose.presentation.components.listOfMenus
import com.example.movieappcompose.ui.theme.MovieAppComposeTheme
import com.example.movieappcompose.util.Navigation
import com.example.movieappcompose.util.Screen
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppComposeTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        bottomBar = {
                            if(shouldShowBottomBar(navBackStackEntry)) {
                                ExpandableBottomNavigationBar(
                                    menuItems = listOfMenus,
                                    navController = navController,
                                    onBottomMenuItemClicked = {
                                        navController.navigate(it.route) {
                                            popUpTo(Screen.HomeScreen.route)
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }
                        }
                    ) {
                        Navigation(navController = navController)
                    }
                }
            }
        }
    }

    private fun shouldShowBottomBar(backStackEntry: NavBackStackEntry?): Boolean {
        return backStackEntry?.destination?.route in listOf(
            Screen.HomeScreen.route,
            Screen.VideoScreen.route,
            Screen.FavouritesScreen.route,
            Screen.PersonScreen.route
        )
    }
}
