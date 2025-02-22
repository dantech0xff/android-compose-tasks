package com.creative.androidtasks.ui.pagertab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Created by dan on 22/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */
 
@Composable
fun TaskListPage(state: String) {
    Box(
        modifier = Modifier.fillMaxSize().padding(12.dp).background(
            color = Color.Black.copy(0.1f),
            shape = RoundedCornerShape(12.dp)
        ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "Page $state",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}