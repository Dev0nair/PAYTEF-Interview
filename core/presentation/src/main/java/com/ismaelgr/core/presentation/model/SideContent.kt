package com.ismaelgr.core.presentation.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
data class SideContent(
    val icon: ImageVector,
    val contentDescription: String? = null,
    val onClick: () -> Unit
)