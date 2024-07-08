package com.ismaelgr.core.presentation.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ismaelgr.core.domain.entity.User
import com.ismaelgr.core.domain.entity.UserType
import com.ismaelgr.core.presentation.components.DashboardButtonComponent
import com.ismaelgr.core.presentation.components.SecondaryTitle
import com.ismaelgr.core.presentation.components.ToolbarComponent
import com.ismaelgr.core.presentation.model.DashboardItem
import com.ismaelgr.core.presentation.screens.base.LoadingScreen
import com.ismaelgr.core.presentation.screens.base.Screen
import com.ismaelgr.core.presentation.utils.onlyFirstUpper
import java.util.UUID

@Composable
fun DashboardScreen(
    onCasesClick: (userId: UUID) -> Unit,
    goToErrorScreen: () -> Unit
) {
    val viewModel: DashboardViewModel = hiltViewModel()
    val state: DashboardViewModel.State by viewModel.state.collectAsState()
    
    LaunchedEffect(key1 = state) {
        if (state is DashboardViewModel.State.Error) {
            goToErrorScreen()
        }
    }
    
    LaunchedEffect(key1 = Unit) {
        viewModel.loadRandomUser()
    }
    
    when (state) {
        is DashboardViewModel.State.Loading -> LoadingScreen()
        is DashboardViewModel.State.Data -> View(actualUser = (state as DashboardViewModel.State.Data).user, onCasesClick = onCasesClick)
        else -> Unit
    }
    
}

@Composable
private fun View(
    actualUser: User,
    onCasesClick: (userId: UUID) -> Unit,
) {
    val optionList: List<DashboardItem> = listOf(
        DashboardItem(route = "studio", onClick = { }),
        DashboardItem(route = "cases", onClick = { onCasesClick(actualUser.id) }),
        DashboardItem(route = "agenda", onClick = { }),
        DashboardItem(route = "contact", onClick = { }),
        DashboardItem(route = "payment", onClick = { }),
        DashboardItem(route = "configuration", onClick = { })
    )
    
    Screen(
        toolbar = {
            ToolbarComponent(title = "Dashboard")
        }
    ) {
        SecondaryTitle(text = "${actualUser.userType.name.onlyFirstUpper()}: ${actualUser.name}")
        
        Spacer(modifier = Modifier.weight(1f))
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(optionList, key = { it.route }) { item ->
                DashboardButtonComponent(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1 / 1f), name = item.name, onClick = item.onClick
                )
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    View(
        actualUser = User(id = UUID.randomUUID(), name = "Ismael", userType = UserType.LAWER),
        onCasesClick = { }
    )
}