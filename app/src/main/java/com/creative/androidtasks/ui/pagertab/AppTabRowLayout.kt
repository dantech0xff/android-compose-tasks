package com.creative.androidtasks.ui.pagertab

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.creative.androidtasks.ui.pagertab.state.TabUiState

/**
 * Created by dan on 22/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */
 
@Composable
fun AppTabRowLayout(selectedTabIndex: Int, listTabs: List<TabUiState>, onTabSelected: (Int) -> Unit = {}) {
    if (listTabs.size > 3) {
        ScrollableTabRow(selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                TabRowDefaults.PrimaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    width = Dp.Unspecified
                )
            }) {
            repeat(listTabs.size) { tabIndex ->
                TabItemLayout(
                    state = listTabs[tabIndex],
                    isSelected = selectedTabIndex == tabIndex,
                    onTabSelected = { onTabSelected(tabIndex) }
                )
            }
        }
    } else {
        TabRow(selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            indicator = { tabPositions ->
                TabRowDefaults.PrimaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    width = Dp.Unspecified
                )
            }) {
            repeat(listTabs.size) { tabIndex ->
                TabItemLayout(
                    state = listTabs[tabIndex],
                    isSelected = selectedTabIndex == tabIndex,
                    onTabSelected = { onTabSelected(tabIndex) }
                )
            }
        }
    }
}