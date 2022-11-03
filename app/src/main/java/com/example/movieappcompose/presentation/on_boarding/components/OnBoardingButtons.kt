package com.example.movieappcompose.presentation.on_boarding.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnBoardingButtons(
    modifier: Modifier = Modifier,
    text: String = "",
    borderColor: Color = Color(0XFFC2C6C5),
    backgroundColor: Color = Color.Transparent,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier
            .size(
                width = 175.dp,
                height = 75.dp
            ),
        shape = RoundedCornerShape(35),
        border = BorderStroke(
            width = 0.2.dp,
            color = borderColor
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = Color(0XFFC2C6C5)
        ),
        onClick = {
            onClick()
        }
    ) {
        Text(
            text = text,
            fontSize = 18.sp
        )
    }
}