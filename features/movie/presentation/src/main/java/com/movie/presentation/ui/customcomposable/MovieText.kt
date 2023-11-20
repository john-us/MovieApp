package com.movie.presentation.ui.customcomposable

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.movie.presentation.constant.FontSize

@Composable
fun MovieText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Black,
    fontSize: TextUnit = FontSize.fontSize_14,
    style: TextStyle = TextStyle.Default,
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        style = style,
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun MovieTextPreview() {
    MaterialTheme {
        MovieText(
            text = "My Movie",
            modifier = Modifier.background(color = Color.Green),
            fontSize = FontSize.fontSize_20,
            style = TextStyle(fontSize = 22.sp)

        )
    }
}