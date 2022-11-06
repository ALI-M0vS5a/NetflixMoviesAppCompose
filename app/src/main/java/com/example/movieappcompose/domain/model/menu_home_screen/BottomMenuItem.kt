package com.example.movieappcompose.domain.model.menu_home_screen

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.movieappcompose.util.UiText


data class BottomMenuItem(
    val label: UiText,
    val icon: ImageVector,
    val route: String,
    var badgeCount: Int = 0
)
