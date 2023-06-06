package com.movie.presentation.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.movie.presentation.constant.back


@Composable
fun BaseScreen(
    title: String,
    showBackButton: Boolean = false,
    onBackClicked: (() -> Unit)? = null,
    content: @Composable () -> Unit
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
    onBackClicked: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Row {
                if (showBackButton) {
                    IconButton(onClick = { onBackClicked?.invoke() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = back)
                    }
                }
                Text(text = title)
            }
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}



