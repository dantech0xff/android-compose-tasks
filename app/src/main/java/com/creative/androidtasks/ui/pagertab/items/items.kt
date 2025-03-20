package com.creative.androidtasks.ui.pagertab.items

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.creative.androidtasks.R
import com.creative.androidtasks.TaskDelegate
import com.creative.androidtasks.ui.pagertab.TaskItemLayout
import com.creative.androidtasks.ui.pagertab.state.TaskGroupUiState
import com.creative.androidtasks.ui.pagertab.state.TaskPageUiState
import com.creative.androidtasks.ui.pagertab.state.TaskUiState

/**
 * Created by dan on 20/3/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */

val itemBgColor = Color.LightGray

fun LazyListScope.emptyState(key: String, state: TaskPageUiState) {
    if (state.activeTaskList.isEmpty()) {
        item(key) {
            Column(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().background(
                    color = itemBgColor
                ).padding(12.dp).animateItem(
                    tween(easing = LinearEasing),
                    null,
                    tween(easing = LinearEasing)
                ),
                verticalArrangement = Arrangement.Top,
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
    }
}

fun LazyListScope.topCorner(key: String? = null) {
    item(key) {
        Box(
            modifier = Modifier.fillMaxWidth().height(12.dp).background(
                color = itemBgColor,
                shape = RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp)
            )
        )
    }
}

fun LazyListScope.bottomCorner(key: String? = null) {
    item(key) {
        Box(
            modifier = Modifier.fillMaxWidth().height(12.dp).background(
                color = itemBgColor,
                shape = RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp)
            )
        )
    }
}

fun LazyListScope.activeTasksHeader(key: String, state: TaskGroupUiState, taskDelegate: TaskDelegate) {
    if (state.page.activeTaskList.isNotEmpty()) {
        item(key) {
            Row(
                modifier = Modifier.background(color = itemBgColor).wrapContentSize()
                    .padding(horizontal = 12.dp).animateItem(
                        tween(easing = LinearEasing), null, tween(easing = LinearEasing)
                    )
            ) {
                Text(
                    state.tab.title, style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1.0f), textAlign = TextAlign.Start
                )
                Text(
                    "S", style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.clickable {
                        taskDelegate.requestSortTasks(state.tab.id)
                    }.padding(vertical = 4.dp, horizontal = 8.dp)
                )
                if (state.tab.id > 0) {
                    Text(
                        "D",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.clickable {
                            taskDelegate.requestUpdateCollection(state.tab.id)
                        }.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

fun LazyListScope.listTaskItems(key: String, state: List<TaskUiState>, taskDelegate: TaskDelegate) {
    itemsIndexed(state, key = { _, item -> "$key${item.id}" }, contentType = { _, item -> item::class.java.name }) { _, item ->
        TaskItemLayout(item, taskDelegate)
    }
}

fun LazyListScope.spacer(height: Int) {
    item {
        Spacer(modifier = Modifier.height(height.dp))
    }
}
