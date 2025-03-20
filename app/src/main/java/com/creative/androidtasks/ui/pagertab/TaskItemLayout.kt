package com.creative.androidtasks.ui.pagertab

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.creative.androidtasks.TaskDelegate
import com.creative.androidtasks.ui.pagertab.items.itemBgColor
import com.creative.androidtasks.ui.pagertab.state.TaskUiState

/**
 * Created by dan on 22/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */

@Composable
fun LazyItemScope.TaskItemLayout(state: TaskUiState, taskDelegate: TaskDelegate) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable {}
            .background(color = itemBgColor)
            .animateItem(
                tween(easing = LinearEasing),
                tween(easing = LinearEasing),
                tween(easing = LinearEasing)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = state.isCompleted,
            onCheckedChange = {
                taskDelegate.invertTaskCompleted(state)
            }
        )
        Column(
            modifier = Modifier.weight(1.0f).wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = state.content,
                modifier = Modifier.padding(horizontal = 4.dp),
                textDecoration = TextDecoration.LineThrough.takeIf { state.isCompleted }
            )
            if (state.isCompleted) {
                Text(text = "Completed: ${state.stringUpdatedAt}", modifier = Modifier.padding(horizontal = 4.dp),)
            }
        }
        if (!state.isCompleted) {
            Text(if (state.isFavorite) "👍" else "👎", modifier = Modifier.clickable {
                taskDelegate.invertTaskFavorite(state)
            })
        }
    }
}
