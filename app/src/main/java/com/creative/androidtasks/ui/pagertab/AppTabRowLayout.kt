package com.creative.androidtasks.ui.pagertab

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

/**
 * Created by dan on 22/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */
 
@Composable
fun AppTabRowLayout(selectedTabIndex: Int, tabCount: Int, onTabSelected: (Int) -> Unit = {}) {
    TabRow(selectedTabIndex,
        modifier = Modifier.fillMaxWidth(),
        indicator = { tabPositions ->
            TabRowDefaults.PrimaryIndicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                width = Dp.Unspecified
            )
        }) {
        repeat(tabCount) { tabIndex ->
            TabItemLayout(
                state = tabIndex.toString(),
                isSelected = (selectedTabIndex == tabIndex),
                onTabSelected = { onTabSelected(tabIndex) }
            )
        }
    }
}