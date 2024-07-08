package com.ismaelgr.core.presentation.screens.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ismaelgr.core.presentation.components.Body
import com.ismaelgr.core.presentation.components.Title

@Composable
fun ErrorScreen(
    onBackClick: () -> Unit,
    goDashboard: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Title(text = "Something bad happened")
        Row {
            Button(onClick = onBackClick) {
                Body(text = "Go back")
            }
            Button(onClick = goDashboard) {
                Body(text = "Go Dashboard")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    ErrorScreen(
        onBackClick = { },
        goDashboard = { }
    )
}