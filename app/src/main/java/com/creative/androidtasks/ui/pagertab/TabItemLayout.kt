package com.creative.androidtasks.ui.pagertab

import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * Created by dan on 22/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */
 
@Composable
fun TabItemLayout(state: String, isSelected: Boolean, onTabSelected: () -> Unit) {
    Tab(
        text = {
            Text("Tab $state")
        },
        selected = isSelected,
        onClick = onTabSelected
    )
}