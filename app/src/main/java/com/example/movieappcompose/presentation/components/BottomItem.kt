package com.example.movieappcompose.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieappcompose.domain.model.bottom_bar_menu.BottomMenuItem
import kotlinx.coroutines.delay
import java.util.*

@Composable
fun BottomItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    selectedColor: Color = Color(0xFF9F2628),
    unSelectedColor: Color = Color.Transparent,
    selectedColorBadge: Color = Color.White,
    unSelectedColorBadge: Color = Color.Gray,
    menuItem: BottomMenuItem,
    onSelected: () -> Unit

) {
    val itemColor by animateColorAsState(targetValue = if (selected) selectedColorBadge else unSelectedColorBadge)

    var badgeNum by remember {
        mutableStateOf(menuItem.badgeCount)
    }
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .animateContentSize()
            .selectable(
                selected = selected,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null,
                enabled = true,
                role = Role.Tab,
                onClick = {
                    onSelected.invoke()
                }
            )
            .background(
                color = if (selected) selectedColor else unSelectedColor,
                shape = CircleShape
            )
            .wrapContentWidth()
            .height(50.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            if(badgeNum > 0) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(selectedColorBadge)
                        .align(Alignment.TopEnd)
                )
            }
            Spacer(modifier = Modifier.width(50.dp))
            Icon(
                imageVector = menuItem.icon,
                contentDescription = menuItem.label.asString(),
                tint = itemColor
            )
        }
        AnimatedVisibility(visible = selected) {
            Text(
                text = menuItem.label.asString().uppercase(Locale.getDefault()),
                fontSize = 12.sp,
                color = itemColor,
                letterSpacing = 4.sp
            )
            LaunchedEffect(key1 = badgeNum) {
                delay(400)
                badgeNum = 0
            }
        }
        Spacer(modifier = Modifier.width(17.dp))
    }
}