package com.creative.androidtasks.ui.pagertab

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by dan on 22/2/25
 *
 * Copyright © 2025 1010 Creative. All rights reserved.
 */
 
@Composable
fun PagerTabLayout() {
    val pageCount by remember { mutableIntStateOf(2) }
    val pagerState = rememberPagerState { pageCount }
    val scope = rememberCoroutineScope()
    AppTabRowLayout(
        selectedTabIndex = pagerState.currentPage,
        tabCount = pageCount,
        onTabSelected = { index ->
            scope.launch {
                pagerState.scrollToPage(index)
            }
        }
    )
    HorizontalPager(pagerState, key = { it }) { pageIndex ->
        TaskListPage(state = pageIndex.toString())
    }
}