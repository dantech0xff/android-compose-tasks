package com.creative.androidtasks.ui.pagertab

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.creative.androidtasks.ID_ADD_NEW_LIST
import com.creative.androidtasks.TaskDelegate
import com.creative.androidtasks.ui.pagertab.state.TabUiState
import com.creative.androidtasks.ui.pagertab.state.TaskGroupUiState
import com.creative.androidtasks.ui.pagertab.state.TaskPageUiState
import com.creative.androidtasks.ui.pagertab.state.TaskUiState
import kotlinx.coroutines.launch
import java.util.Calendar

/**
 * Created by dan on 22/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */
 
@Composable
fun PagerTabLayout(state: List<TaskGroupUiState>, taskDelegate: TaskDelegate) {
    var pageCount by remember { mutableIntStateOf(0) }
    var internalState by remember { mutableStateOf(state) }
    internalState = state
    val pagerState = rememberPagerState { pageCount }
    val scope = rememberCoroutineScope()
    pageCount = state.count {
        it.tab.id != ID_ADD_NEW_LIST
    }

    LaunchedEffect(Unit) {
        snapshotFlow { pagerState.currentPage }.collect { index ->
            internalState.getOrNull(index)?.tab?.id?.let { currentCollectionId ->
                taskDelegate.updateCurrentCollectionId(currentCollectionId)
            }
        }
    }

    AppTabRowLayout(
        selectedTabIndex = pagerState.currentPage,
        listTabs = state.map { it.tab },
        onTabSelected = { index ->
            if ((state.getOrNull(index)?.tab?.id ?: 0) == ID_ADD_NEW_LIST) {
                taskDelegate.requestAddNewCollection()
            } else {
                scope.launch {
                    pagerState.scrollToPage(index)
                }
            }
        }
    )
    HorizontalPager(
        pagerState, key = { it },
        beyondViewportPageCount = 2
    ) { pageIndex ->
        TaskListPage(collectionId = state[pageIndex].tab.id, state = state[pageIndex].page, taskDelegate)
    }
}