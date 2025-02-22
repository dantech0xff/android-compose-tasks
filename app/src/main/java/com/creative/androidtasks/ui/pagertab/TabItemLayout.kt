package com.creative.androidtasks.ui.pagertab

import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.creative.androidtasks.ui.pagertab.state.TabUiState

/**
 * Created by dan on 22/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */
 
@Composable
fun TabItemLayout(state: TabUiState, isSelected: Boolean, onTabSelected: () -> Unit) {
    Tab(text = { Text(state.title) }, selected = isSelected, onClick = onTabSelected)
}