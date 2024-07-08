package com.ismaelgr.paytefapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ismaelgr.core.presentation.screens.base.ErrorScreen
import com.ismaelgr.core.presentation.screens.cases.CasesScreen
import com.ismaelgr.core.presentation.screens.dashboard.DashboardScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "dashboard") {
        composable(route = "dashboard") {
            DashboardScreen(
                onCasesClick = { userId -> navController.navigate("cases/${userId}") },
                goToErrorScreen = { navController.navigate("error") }
            )
        }
        
        composable(route = "cases/{userId}") {
            CasesScreen(
                onBackClick = navController::navigateUp,
                goToErrorScreen = { navController.navigate("error") }
            )
        }
        
        composable(route = "error") {
            ErrorScreen(
                onBackClick = navController::navigateUp,
                goDashboard = {
                    navController.navigate("dashboard") {
                        popUpTo("dashboard")
                    }
                }
            )
        }
    }
    
}