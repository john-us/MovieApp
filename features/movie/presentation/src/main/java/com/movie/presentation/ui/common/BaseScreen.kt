package com.movie.presentation.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.movie.presentation.constant.FontSize
import com.movie.presentation.constant.back
import com.movie.presentation.ui.customcomposable.MovieText


@Composable
fun BaseScreen(
    title: String,
    showBackButton: Boolean = false,
    onBackClicked: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        AppBar(
            title = title,
            showBackButton = showBackButton,
            onBackClicked = onBackClicked
        )
        content()
    }

}

@Composable
fun AppBar(
    title: String,
    showBackButton: Boolean = false,
    onBackClicked: (() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showBackButton) {
                    IconButton(onClick = { onBackClicked?.invoke() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = back)
                    }
                }
                MovieText(
                    text = title,
                    color = Color.White,
                    fontSize = FontSize.fontSize_20,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
            }
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}



