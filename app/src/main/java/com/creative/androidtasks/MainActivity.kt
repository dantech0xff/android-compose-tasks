package com.creative.androidtasks

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.creative.androidtasks.datastore.AppSettingImpl
import com.creative.androidtasks.ui.floataction.XFloatActionButton
import com.creative.androidtasks.ui.theme.AndroidTasksTheme
import com.creative.androidtasks.ui.topbar.TopBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidTasksTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
                    XFloatActionButton(Modifier.background(
                        color = Color.Black.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp)
                    ).size(58.dp).clip(RoundedCornerShape(12.dp))) {
                        Log.d("MainActivity", "FloatingActionButton Clicked")
                    }
                }) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TopBar(Modifier.fillMaxWidth().height(52.dp).padding(horizontal = 12.dp))

                        var pageCount by remember { mutableIntStateOf(2) }
                        val pagerState = rememberPagerState { pageCount }
                        val scope = rememberCoroutineScope()
                        ScrollableTabRow(pagerState.currentPage,
                            edgePadding = 12.dp,
                            indicator = { tabPositions ->
                                TabRowDefaults.PrimaryIndicator(
                                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                                    width = Dp.Unspecified
                                )
                            }) {
                            repeat(pageCount + 1) { tabIndex ->
                                Tab(
                                    text = {
                                        if (tabIndex < pageCount)
                                            Text("Tab $tabIndex")
                                        else {
                                            Text("+ New list")
                                        }
                                    },
                                    selected = (pagerState.currentPage == tabIndex),
                                    onClick = {
                                        Log.d("MainActivity", "Tab $tabIndex Clicked")
                                        if (tabIndex == pageCount) {
                                            pageCount += 1
                                        }
                                        scope.launch {
                                            pagerState.scrollToPage(tabIndex)
                                        }
                                    }
                                )
                            }
                        }
                        HorizontalPager(pagerState, key = {
                            it
                        }, beyondViewportPageCount = 2) { pageIndex ->
                            Box(
                                modifier = Modifier.fillMaxSize().padding(12.dp).background(
                                    color = Color.Black.copy(0.1f),
                                    shape = RoundedCornerShape(12.dp)
                                ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Page $pageIndex",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
