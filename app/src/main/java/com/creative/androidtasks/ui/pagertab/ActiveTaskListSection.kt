package com.creative.androidtasks.ui.pagertab

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.creative.androidtasks.R
import com.creative.androidtasks.TaskDelegate
import com.creative.androidtasks.ui.pagertab.state.TaskUiState

/**
 * Created by dan on 22/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */
 
@Composable
fun ActiveTaskListSection(activeTaskList: List<TaskUiState>, taskDelegate: TaskDelegate) {
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
        AnimatedVisibility(
            visible = activeTaskList.isEmpty()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val lottieComposition by rememberLottieComposition(
                    spec = LottieCompositionSpec.RawRes(R.raw.lottie_empty_01)
                )
                LottieAnimation(lottieComposition)
                Text("All Tasks Completed", style = MaterialTheme.typography.headlineMedium)
                Text("Nice work!", style = MaterialTheme.typography.bodyMedium)
            }
        }

        activeTaskList.forEach {
            TaskItemLayout(it, onCompleteTask = { taskState ->
                taskDelegate.invertTaskCompleted(taskState)
                Log.d("TaskItemLayout", "onCompleteTask: $taskState")
            }, onTaskClicked = { taskState ->
                Log.d("TaskItemLayout", "onTaskClicked: $taskState")
            }, onTaskFavorite = { taskState ->
                taskDelegate.invertTaskFavorite(taskState)
                Log.d("TaskItemLayout", "onTaskFavorite: $taskState")
            })
        }
    }
}