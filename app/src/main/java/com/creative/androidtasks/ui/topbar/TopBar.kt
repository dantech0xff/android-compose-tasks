package com.creative.androidtasks.ui.topbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Created by dan on 15/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */

@Composable
fun TopBar() {
    Box(
        modifier = Modifier.fillMaxWidth().height(52.dp).padding(horizontal = 12.dp)
    ) {
        Text(
            "Tasks", style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

