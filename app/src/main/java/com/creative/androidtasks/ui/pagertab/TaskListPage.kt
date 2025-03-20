package com.creative.androidtasks.ui.pagertab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.creative.androidtasks.TaskDelegate
import com.creative.androidtasks.ui.pagertab.items.activeTasksHeader
import com.creative.androidtasks.ui.pagertab.items.bottomCorner
import com.creative.androidtasks.ui.pagertab.items.emptyState
import com.creative.androidtasks.ui.pagertab.items.listTaskItems
import com.creative.androidtasks.ui.pagertab.items.spacer
import com.creative.androidtasks.ui.pagertab.items.topCorner
import com.creative.androidtasks.ui.pagertab.state.TaskGroupUiState

/**
 * Created by dan on 22/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */

@Composable
fun TaskListPage(state: TaskGroupUiState, taskDelegate: TaskDelegate) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight()
            .padding(12.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        topCorner()
        activeTasksHeader("header", state, taskDelegate)
        emptyState("empty", state.page)
        listTaskItems("active", state.page.activeTaskList, taskDelegate)
        bottomCorner()
        spacer(24)

        topCorner()
        listTaskItems("completed", state.page.completedTaskList, taskDelegate)
        bottomCorner()
    }
}