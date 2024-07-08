package com.ismaelgr.core.presentation.screens.cases

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ismaelgr.core.domain.entity.Case
import com.ismaelgr.core.presentation.components.CaseListItem
import com.ismaelgr.core.presentation.components.ToolbarComponent
import com.ismaelgr.core.presentation.model.SideContent
import com.ismaelgr.core.presentation.screens.base.LoadingScreen
import com.ismaelgr.core.presentation.screens.base.Screen

@Composable
fun CasesScreen(
    onBackClick: () -> Unit,
    goToErrorScreen: () -> Unit
) {
    val viewModel: CasesViewModel = hiltViewModel()
    val state: CasesViewModel.State by viewModel.state.collectAsState()
    
    LaunchedEffect(key1 = Unit) {
        viewModel.loadData()
    }
    
    LaunchedEffect(key1 = state) {
        if (state is CasesViewModel.State.Error) {
            goToErrorScreen()
        }
    }
    
    when (state) {
        is CasesViewModel.State.Loading -> LoadingScreen()
        is CasesViewModel.State.Data -> View((state as CasesViewModel.State.Data).data, onBackClick, viewModel::onCreateCaseClick)
        else -> Unit
    }
}

@Composable
private fun View(data: List<Case>, onBackClick: () -> Unit, onCreateClick: () -> Unit) {
    Screen(
        toolbar = {
            ToolbarComponent(
                title = "Cases",
                leftSide = listOf(SideContent(Icons.AutoMirrored.Default.ArrowBack, onClick = onBackClick)),
                rightSide = listOf(SideContent(Icons.Default.Create, onClick = onCreateClick))
            )
        }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(data, key = { it.id }) { case ->
                CaseListItem(case = case)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    View(
        data = listOf(),
        onBackClick = { },
        onCreateClick = { }
    )
}