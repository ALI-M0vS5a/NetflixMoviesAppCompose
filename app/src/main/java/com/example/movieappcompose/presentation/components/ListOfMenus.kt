package com.example.movieappcompose.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.movieappcompose.R
import com.example.movieappcompose.domain.model.menu_home_screen.BottomMenuItem
import com.example.movieappcompose.util.Screen
import com.example.movieappcompose.util.UiText
import kotlin.random.Random


val listOfMenus = listOf(
    BottomMenuItem(
        label = UiText.StringResource(resId = R.string.home),
        icon = Icons.Default.Home,
        route = Screen.HomeScreen.route,
        badgeCount = Random.nextInt(2)
    ),
    BottomMenuItem(
        label = UiText.StringResource(resId = R.string.video),
        icon = Icons.Default.Watch,
        route = Screen.VideoScreen.route,
        badgeCount = Random.nextInt(2)
    ),
    BottomMenuItem(
        label = UiText.StringResource(resId = R.string.favourites),
        icon = Icons.Default.Favorite,
        route = Screen.FavouritesScreen.route,
        badgeCount = Random.nextInt(2)
    ),
    BottomMenuItem(
        label = UiText.StringResource(resId = R.string.person),
        icon = Icons.Default.Person,
        route = Screen.PersonScreen.route,
        badgeCount = Random.nextInt(2)
    )
)