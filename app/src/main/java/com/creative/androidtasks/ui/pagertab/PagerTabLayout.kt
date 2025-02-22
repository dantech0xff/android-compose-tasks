package com.creative.androidtasks.ui.pagertab

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.creative.androidtasks.ui.pagertab.state.TabUiState
import com.creative.androidtasks.ui.pagertab.state.TaskPageUiState
import kotlinx.coroutines.launch

/**
 * Created by dan on 22/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */
 
@Composable
fun PagerTabLayout() {
    var pageCount by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState { pageCount }
    val scope = rememberCoroutineScope()
    val listTabs = listOf(
        TabUiState(
            id = 1,
            title = "Tab 1"
        ), TabUiState(
            id = 2,
            title = "Tab 2"
        )
    )
    val listTaskPage = listOf(TaskPageUiState(listOf()), TaskPageUiState(listOf()))
    pageCount = listTabs.size
    AppTabRowLayout(
        selectedTabIndex = pagerState.currentPage,
        listTabs = listTabs,
        onTabSelected = { index ->
            scope.launch {
                pagerState.scrollToPage(index)
            }
        }
    )
    HorizontalPager(pagerState, key = { it }) { pageIndex ->
        TaskListPage(state = listTaskPage[pageIndex])
    }
}