package com.example.movieappcompose.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.movieappcompose.domain.model.bottom_bar_menu.BottomMenuItem

@Composable
fun ExpandableBottomNavigationBar(
    menuItems: List<BottomMenuItem>,
    modifier: Modifier = Modifier,
    navController: NavController,
    backgroundColor: Color = Color(0xFF232731),
    borderColor: Color = Color(0xFF232731),
    roundedShape: RoundedCornerShape = RoundedCornerShape(
        topEnd = 36.dp, topStart = 36.dp
    ),
    onBottomMenuItemClicked: (BottomMenuItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier
            .graphicsLayer { shape = roundedShape }
            .clip(roundedShape)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = roundedShape
            )
            .fillMaxWidth()
            .height(100.dp),
        elevation = 8.dp,
        backgroundColor = backgroundColor
    ) {
        menuItems.forEachIndexed { index, menuItem ->
            val selected = backStackEntry.value?.destination?.route == menuItem.route
            when(index) {
                0 -> {
                    BottomItem(
                        selected = selected,
                        menuItem = menuItem,
                        modifier = modifier.padding(start = 16.dp).align(Alignment.CenterVertically)
                    ) {
                        onBottomMenuItemClicked(menuItem)
                    }
                }
                menuItems.lastIndex -> {
                    BottomItem(
                        modifier = modifier.padding(end = 16.dp).align(Alignment.CenterVertically),
                        selected = selected,
                        menuItem = menuItem
                    ) {
                        onBottomMenuItemClicked(menuItem)
                    }
                }
                else -> {
                    BottomItem(
                        modifier = modifier.align(Alignment.CenterVertically),
                        selected = selected,
                        menuItem = menuItem
                    ) {
                        onBottomMenuItemClicked(menuItem)
                    }
                }
            }
        }
    }
}