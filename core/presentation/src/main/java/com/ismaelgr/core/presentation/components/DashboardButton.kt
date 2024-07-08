package com.ismaelgr.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DashboardButtonComponent(
    modifier: Modifier = Modifier,
    name: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(15.dp))
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Title(modifier = Modifier.padding(10.dp), text = name)
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    Column {
        DashboardButtonComponent(name = "Casos", onClick = {})
    }
}