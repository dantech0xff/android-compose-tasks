package com.creative.androidtasks.ui.pagertab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.creative.androidtasks.TaskDelegate
import com.creative.androidtasks.ui.pagertab.state.TaskUiState

/**
 * Created by dan on 22/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */
 
@Composable
fun CompletedTaskListSection(completedTaskList: List<TaskUiState>, taskDelegate: TaskDelegate) {
    AnimatedVisibility(completedTaskList.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = Color.Black.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(vertical = 12.dp)
                .animateContentSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            completedTaskList.forEachIndexed { _, taskUiState ->
                key(taskUiState.id) {
                    TaskItemLayout(taskUiState, taskDelegate)
                }
            }
        }
    }
}