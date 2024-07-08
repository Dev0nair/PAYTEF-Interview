package com.ismaelgr.core.presentation.model

import androidx.compose.runtime.Immutable
import com.ismaelgr.core.presentation.utils.onlyFirstUpper

@Immutable
data class DashboardItem(
    val route: String,
    val onClick: () -> Unit,
    val name: String = route.onlyFirstUpper()
)
