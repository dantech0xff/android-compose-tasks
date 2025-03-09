package com.creative.androidtasks.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.creative.androidtasks.ui.home.HomeLayout
import com.creative.androidtasks.ui.theme.AndroidTasksTheme

/**
 * Created by dan on 9/3/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */
 
@Composable
fun TasksApp() {
    AndroidTasksTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            modifier = Modifier.fillMaxSize(),
            startDestination = NavScreen.HOME.route
        ) {
            composable(route = NavScreen.HOME.route) {
                HomeLayout()
            }
        }
    }
}

enum class NavScreen(val route: String) {
    HOME("home"),
    TASK("task/{taskId}"),
    COLLECTION("collection/{collectionId}"),
    SETTINGS("settings")
}