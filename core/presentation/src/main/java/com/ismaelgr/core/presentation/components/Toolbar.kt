package com.ismaelgr.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ismaelgr.core.presentation.model.SideContent

@Composable
fun ToolbarComponent(
    title: String,
    leftSide: List<SideContent> = emptyList(),
    rightSide: List<SideContent> = emptyList(),
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
            .padding(horizontal = 10.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.defaultMinSize(minHeight = 24.dp, minWidth = 24.dp)
        ) {
            leftSide.map { content ->
                Icon(modifier = Modifier.clickable(onClick = content.onClick), imageVector = content.icon, contentDescription = content.contentDescription)
            }
        }
        Title(text = title)
        Row(
            modifier = Modifier.defaultMinSize(minHeight = 24.dp, minWidth = 24.dp)
        ) {
            rightSide.map { content ->
                Icon(modifier = Modifier.clickable(onClick = content.onClick), imageVector = content.icon, contentDescription = content.contentDescription)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        ToolbarComponent(title = "Dashboard")
        ToolbarComponent(title = "Cases", leftSide = listOf(SideContent(Icons.AutoMirrored.Default.ArrowBack, onClick = {})))
    }
}