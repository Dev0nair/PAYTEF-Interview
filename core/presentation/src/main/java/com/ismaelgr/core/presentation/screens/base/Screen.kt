package com.ismaelgr.core.presentation.screens.base

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Screen(
    modifier: Modifier = Modifier,
    toolbar: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val columnContent: @Composable () -> Unit = {
        Column(
            modifier = modifier
                .padding(10.dp),
            verticalArrangement = spacedBy(10.dp)
        ) {
            content()
        }
    }
    
    if(toolbar != null) {
        Column(
            Modifier.fillMaxSize()
        ) {
            toolbar()
            columnContent()
        }
    } else {
        columnContent()
    }
}