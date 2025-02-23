package com.creative.androidtasks.ui.pagertab

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.creative.androidtasks.ui.pagertab.state.TaskUiState

/**
 * Created by dan on 22/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */
 
@Composable
fun TaskItemLayout(
    state: TaskUiState,
    onCompleteTask: (TaskUiState) -> Unit = {},
    onTaskClicked: (TaskUiState) -> Unit = {},
    onTaskFavorite: (TaskUiState) -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable {
            onTaskClicked(state)
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = state.isCompleted,
            onCheckedChange = {
                onCompleteTask(state)
            }
        )
        Text(
            text = state.content,
            modifier = Modifier.weight(1.0f).padding(horizontal = 4.dp)
        )
        Text(if (state.isFavorite) "👍" else "👎", modifier = Modifier.padding(6.dp).clickable {
            onTaskFavorite(state)
        })
    }
}

@Preview
@Composable
fun TaskItemLayoutPreview() {
    TaskItemLayout(
        TaskUiState(
            content = "Task 1",
            isCompleted = false,
            isFavorite = true,
            collectionId = 1,
            id = 1,
            updatedAt = 1000
        )
    )
}