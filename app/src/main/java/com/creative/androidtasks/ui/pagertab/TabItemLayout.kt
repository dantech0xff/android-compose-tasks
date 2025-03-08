package com.creative.androidtasks.ui.pagertab

import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.creative.androidtasks.ID_ADD_NEW_LIST
import com.creative.androidtasks.ID_FAVORITE_LIST
import com.creative.androidtasks.ui.pagertab.state.TabUiState

/**
 * Created by dan on 22/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */
 
@Composable
fun TabItemLayout(state: TabUiState, isSelected: Boolean, onTabSelected: () -> Unit) {
    Tab(text = {
        Text(
            state.title, color = when (state.id) {
                ID_FAVORITE_LIST -> {
                    Color.Yellow
                }
                ID_ADD_NEW_LIST -> {
                    Color.Blue
                }
                else -> {
                    Color.Unspecified
                }
            }
        )
    }, selected = isSelected, onClick = onTabSelected)
}